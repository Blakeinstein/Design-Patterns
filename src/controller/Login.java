package controller;

import util.Files;
import models.Person;
import models.Seller;
import util.Utils;

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
        assert this.users.containsKey(userName) == false : String.format("User %s already exists", userName);
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
            Files.WriteLineToFile(fileName, String.format("%s:%s", userName, password));
        }
    }

    public static Login GetInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    public LoginData userLogin(String userName, String password) throws Exception{
        if (!this.users.containsKey(userName))
            throw new Exception("User not found");
        var userData = this.users.get(userName);

        if (!userData.password.equals(password))
            throw new Exception("Invalid password.");
        return userData;
    }
}
