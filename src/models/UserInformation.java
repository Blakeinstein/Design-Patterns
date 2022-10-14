package models;

import controller.Login;

public class UserInformation {
    public Login.USER_TYPE USERTYPE;
    public String userName;
    public String password;

    public UserInformation(String userName, String password, Login.USER_TYPE USERTYPE) {
        this.USERTYPE = USERTYPE;
        this.userName = userName;
        this.password = password;
    }
}
