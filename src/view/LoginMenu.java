package view;

import controller.Login;

import javax.swing.*;
import java.awt.event.*;

public class LoginMenu extends JDialog {
    public static class LoginFormActions {
        public void onOk(String userName, String password, Login.USER_TYPE USERTYPE, boolean isRegister) throws Exception{}
    }
    private final LoginFormActions formActions;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField;
    private JTextField userNameField;
    private JRadioButton isBuyer;
    private JPanel accountTypeRadio;
    private JLabel accountTypeLabel;

    private final boolean isRegister;

    public LoginMenu(boolean isRegister, LoginFormActions formActions) {
        this.formActions = formActions;
        this.isRegister = isRegister;
        setTitle(isRegister ? "Create new user" : "Login");
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> dispose());

        // call dispose()() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call dispose()() on ESCAPE
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.accountTypeRadio.setVisible(isRegister);
        this.accountTypeLabel.setVisible(isRegister);
    }

    private void onOK() {
        // add your code here
        try {
            if (this.userNameField.getText().isEmpty()) throw new Exception("Please fill in username");
            if (this.passwordField.getPassword().length == 0) throw new Exception("Please fill in password");

            this.formActions.onOk(
                    this.userNameField.getText(),
                    String.valueOf(this.passwordField.getPassword()),
                    this.isBuyer.isSelected() ? Login.USER_TYPE.Buyer : Login.USER_TYPE.Seller,
                    this.isRegister
            );
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
