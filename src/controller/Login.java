package controller;

import models.Buyer;
import util.Files;
import models.Person;
import models.Seller;
import util.Utils;
import view.AppView;
import view.LoginMenu;

import javax.swing.*;
import java.util.HashMap;

/**
 * Login controller class, implements Facade and Singleton design pattern
 */
public class Login {

    /**
     * Enum for user type
     * Buyer = 0
     * Seller = 1
     */
    public enum USER_TYPE {
        Buyer, Seller
    }

    /**
     * Information about available users.
     */
    public static class LoginData {
        /**
         * Name of user
         */
        public final String name;
        /**
         * password for the user
         */
        public final String password;
        /**
         * Corresponding person object
         */
        public final Person person;
        /**
         * Type of the user
         */
        public final USER_TYPE USERTYPE;

        public LoginData(String name, String password, Person person, USER_TYPE USERTYPE) {
            this.name = name;
            this.password = password;
            this.person = person;
            this.USERTYPE = USERTYPE;
        }
    }

    /**
     * Instance of login - Singleton Design Pattern
     */
    private static Login instance = null;

    /**
     * Map of userName to loginData for efficient lookups
     */
    private final HashMap<String, LoginData> users;

    /**
     * Login data for current user trying to log in.
     */
    public LoginData userInfo = null;

    /**
     * public constructor, attempts to read buyer and seller info, and construct internal DB
     */
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
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    e.getMessage(),
                    "Error reading user login info",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Adds a new user to internal db
     * @param userName username of new user
     * @param password password of the new user
     * @param type type of the new user
     * @param updateFile Should update db on disk.
     * @throws Exception if user already exists, or disk write fails
     */

    public void addNewUser(String userName, String password, USER_TYPE type, Boolean updateFile) throws Exception {
        if (this.users.containsKey(userName))
            throw new Exception(String.format("User %s already exists", userName));
        Person p = switch (type) {
            case Buyer -> new Buyer(userName);
            case Seller -> new Seller(userName);
        };
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
            String fileName = switch (type) {
                case Buyer -> "BuyerInfo.txt";
                case Seller -> "SellerInfo.txt";
            };
            Files.WriteLineToFile(fileName, String.format("%s:%s", userName, password));
        }
    }

    /**
     * Gets the instance of the Login singleton
     * @return instance of Login
     */

    public static Login GetInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    /**
     * Attempts user login flow
     * @return true if user logs in successfully.
     */

    public LoginData userLogin() {
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

    /**
     * checks if user exists in internal db
     * @param userName username to check
     * @return true if user exists, false otherwise.
     */
    public boolean hasUser(String userName) {
        return this.users.containsKey(userName);
    }

    /**
     * Returns the map from username to person;
     * @return map of users.
     */
    public HashMap<String, LoginData> getUsers() {
        return this.users;
    }
}
