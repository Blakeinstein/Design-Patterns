package view;

import controller.Facade;

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
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AppView.this.changeLoginState();
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
