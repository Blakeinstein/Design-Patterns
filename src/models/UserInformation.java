package models;

import controller.Login;

public class UserInformation {
    public final Login.USER_TYPE USERTYPE;
    public final String userName;
    public final String password;

    public UserInformation(String userName, String password, Login.USER_TYPE USERTYPE) {
        this.USERTYPE = USERTYPE;
        this.userName = userName;
        this.password = password;
    }
}
