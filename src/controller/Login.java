package controller;

import Input.Input;
import models.Buyer;
import models.Person;
import models.Seller;
import util.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;

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
    private ArrayList<LoginData> users;

    private Login() {
        this.users = new ArrayList<>();
        String buyerInfo = Input.GetBuyerInfo();
        String sellerInfo = Input.GetSellerInfo();
        try {
            var buyerInfoPairs = Utils.GetPairs(buyerInfo);
            for (var parts : buyerInfoPairs) {
                this.users.add(new LoginData(
                        parts[0],
                        parts[1],
                        new Buyer(parts[0]),
                        UserType.Buyer
                ));
            }
            var sellerInfoPairs = Utils.GetPairs(sellerInfo);
            for (var parts : sellerInfoPairs) {
                this.users.add(new LoginData(
                        parts[0],
                        parts[1],
                        new Seller(parts[0]),
                        UserType.Seller
                ));
            }
        } catch (Exception e) {
            System.out.println("Error reading user login info");
            System.out.println(e.getMessage());
        }
    }

    public static Login GetInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    public LoginData userLogin(String userName, String password) throws Exception{
        for (var userData : this.users) {
            if (userData.name == userName && userData.password == password) {
                return userData;
            }
        }
        throw new Exception("User not found.");
    }
}
