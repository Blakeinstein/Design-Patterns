package models;

import controller.Login;

public class UserInformation {
    public Login.UserType userType;
    public String userName;
    public String password;

    public UserInformation(String userName, String password, Login.UserType userType) {
        this.userType = userType;
        this.userName = userName;
        this.password = password;
    }
}
