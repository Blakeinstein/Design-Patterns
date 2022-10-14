package controller;

import util.Files;
import models.Person;
import models.Seller;
import util.Utils;
import view.LoginMenu;

import java.util.HashMap;

public class Login {
    public static enum UserType {
        Buyer, Seller
    }
    public class LoginData {
        public String name;
        public String password;
        public Person person;
        public UserType userType;

        public LoginData(String name, String password, Person person, UserType userType) {
            this.name = name;
            this.password = password;
            this.person = person;
            this.userType = userType;
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
                this.addNewUser(parts[0], parts[1], UserType.Buyer, false);
            }
            var sellerInfoPairs = Utils.GetPairs(sellerInfo);
            for (var parts : sellerInfoPairs) {
                this.addNewUser(parts[0], parts[1], UserType.Seller, false);
            }
        } catch (Exception e) {
            System.out.println("Error reading user login info");
            System.out.println(e.getMessage());
        }
    }

    public void addNewUser(String userName, String password, UserType type, Boolean updateFile) throws Exception {
        if (this.users.containsKey(userName))
            throw new Exception(String.format("User %s already exists", userName));
        this.users.put(
                userName,
                new LoginData(
                        userName,
                        password,
                        new Seller(userName),
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
            Files.WriteToFile(fileName, Login.this.serializeUsers(type));
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
                    public void onOk(String userName, String password, Login.UserType userType, boolean isRegister) throws Exception{
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
        dialog.setVisible(true);

        return this.userInfo;
    }

    public boolean hasUser(String userName) {
        return this.users.containsKey(userName);
    }

    public String serializeUsers(UserType type) {
        StringBuilder sb = new StringBuilder();
        for (var user : this.users.values()) {
            if (user.userType == type) {
                sb.append(String.format("%s:%s", user.name, user.password)).append("\n");
            }
        }
        return sb.toString();
    }
}
