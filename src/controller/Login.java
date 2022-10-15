package controller;

import models.Buyer;
import util.Files;
import models.Person;
import models.Seller;
import util.Utils;
import view.LoginMenu;

import java.util.HashMap;

public class Login {
    public static enum USER_TYPE {
        Buyer, Seller
    }
    public class LoginData {
        public String name;
        public String password;
        public Person person;
        public USER_TYPE USERTYPE;

        public LoginData(String name, String password, Person person, USER_TYPE USERTYPE) {
            this.name = name;
            this.password = password;
            this.person = person;
            this.USERTYPE = USERTYPE;
        }
    }
    private static Login instance = null;
    private HashMap<String, LoginData> users;

    public LoginData userInfo = null;

    private Login() {
        this.users = new HashMap<>();
        String buyerInfo = Files.GetBuyerInfo();
        String sellerInfo = Files.GetSellerInfo();
        try {
            var buyerInfoPairs = Utils.GetPairs(buyerInfo);
            for (var parts : buyerInfoPairs) {
                this.addNewUser(parts[0], parts[1], USER_TYPE.Buyer, false);
            }
            var sellerInfoPairs = Utils.GetPairs(sellerInfo);
            for (var parts : sellerInfoPairs) {
                this.addNewUser(parts[0], parts[1], USER_TYPE.Seller, false);
            }
        } catch (Exception e) {
            System.out.println("Error reading user login info");
            System.out.println(e.getMessage());
        }
    }

    public void addNewUser(String userName, String password, USER_TYPE type, Boolean updateFile) throws Exception {
        if (this.users.containsKey(userName))
            throw new Exception(String.format("User %s already exists", userName));
        Person p;
        switch (type) {
            case Buyer:
                p = new Buyer(userName);
                break;
            case Seller:
            default:
                p = new Seller(userName);
        }
        this.users.put(
                userName,
                new LoginData(
                        userName,
                        password,
                        p,
                        type
                )
        );

        if (updateFile) {
            String fileName;
            switch (type) {
                case Buyer:
                    fileName = "BuyerInfo.txt";
                    break;
                case Seller:
                    fileName = "SellerInfo.txt";
                    break;
                default:
                    throw new Exception("Invalid user type");
            }
            Files.WriteLineToFile(fileName, String.format("%s:%s", userName, password));
        }
    }

    public static Login GetInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    public LoginData userLogin() throws Exception{
        LoginMenu dialog = new LoginMenu(
                false,
                new LoginMenu.LoginFormActions() {
                    public void onOk(String userName, String password, USER_TYPE userType, boolean isRegister) throws Exception{
                        if (!Login.this.users.containsKey(userName))
                            throw new Exception("User not found");
                        var userData = Login.this.users.get(userName);

                        if (!userData.password.equals(password))
                            throw new Exception("Invalid password.");

                        Login.this.userInfo = userData;
                    }
                }
        );
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return this.userInfo;
    }

    public boolean hasUser(String userName) {
        return this.users.containsKey(userName);
    }
}
