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
    private class LoginData {
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
    private Login instance = null;
    private ArrayList<LoginData> users;

    private Login() {
        String buyerInfo = Input.GetBuyerInfo();
        String sellerInfo = Input.GetSellerInfo();
        try {
            BufferedReader br = Utils.GetBufferedReader(buyerInfo);
            String line = null;

            while ( (line = br.readLine()) != null) {
                String[] parts = line.split(":");
                assert parts.length == 2 : "Invalid data";
                this.users.add(new LoginData(
                        parts[0],
                        parts[1],
                        new Buyer(parts[0]),
                        UserType.Buyer
                ));
            }
            br = Utils.GetBufferedReader(sellerInfo);

            while ( (line = br.readLine()) != null) {
                String[] parts = line.split(":");
                assert parts.length == 2 : "Invalid data";
                this.users.add(new LoginData(
                        parts[0],
                        parts[1],
                        new Seller(parts[0]),
                        UserType.Seller
                ));
            }
        } catch (Exception e) {

        }
    }

    public Login GetInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }
}
