package models;

import controller.Login;

/**
 * User infomration DTO
 */
public class UserInformation {
    /**
     * Type of the user
     */
    public final Login.USER_TYPE USERTYPE;
    /**
     * username of the user
     */
    public final String userName;
    /**
     * password of the user
     */
    public final String password;

    public UserInformation(String userName, String password, Login.USER_TYPE USERTYPE) {
        this.USERTYPE = USERTYPE;
        this.userName = userName;
        this.password = password;
    }
}
