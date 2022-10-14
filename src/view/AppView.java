package view;

import controller.Facade;
import controller.Login;
import models.Seller;
import models.UserInformation;
import util.Files;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppView {
    private JButton loginButton;
    private JButton createNewUserButton;
    private JPanel loginActions;
    private JPanel views;
    private JPanel mainFrame;
    private JTextPane loggedInUserInfo;

    private static AppView appInstance;

    private JFrame frame;

    private Facade facade;

    private boolean isUserLoggedIn = false;

    private AppView() {
        this.facade = new Facade();
        this.frame = new JFrame("PTBS");
        this.frame.setContentPane(this.mainFrame);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AppView.this.changeLoginState();
            }
        });
        createNewUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AppView.this.createNewUserFlow();
            }
        });
    }

    public static AppView Get(){
        if (AppView.appInstance == null) {
            AppView.appInstance = new AppView();
        }
        return AppView.appInstance;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void createNewUserFlow() {
        LoginMenu dialog = new LoginMenu(
                true,
                new LoginMenu.LoginFormActions() {
                    public void onOk(String userName, String password, Login.UserType userType, boolean isRegister) throws Exception{
                        if (Login.GetInstance().hasUser(userName))
                            throw new Exception(String.format("User %s already exists", userName));
                        AppView.this.facade.createUser(
                                new UserInformation(userName, password, userType)
                        );
                        JOptionPane.showMessageDialog(
                                AppView.this.frame,
                                String.format("%s: %s created successfully", userType, userName),
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
        );

        dialog.pack();
        dialog.setVisible(true);
    }

    public void changeLoginState() {
        this.isUserLoggedIn = this.isUserLoggedIn ? this.facade.logout() : this.facade.login();
        this.loginButton.setText(this.isUserLoggedIn ? "Logout" : "Login");
        this.createNewUserButton.setVisible(!this.isUserLoggedIn);
        if (this.isUserLoggedIn) {
            this.loggedInUserInfo.setText(String.format("Logged in as %s", this.facade.getLoggedInUserName()));
        }
        this.loggedInUserInfo.setVisible(this.isUserLoggedIn);
    }
}
