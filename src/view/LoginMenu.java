package view;

import controller.Login;

import javax.swing.*;
import java.awt.event.*;

public class LoginMenu extends JDialog {
    public static class LoginFormActions {
        public void onOk(String userName, String password, Login.UserType userType, boolean isRegister) throws Exception{};
        public void onCancel() throws Exception {};
    }
    private final LoginFormActions formActions;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField;
    private JTextField userNameField;
    private JRadioButton isBuyer;
    private JRadioButton isSeller;
    private JPanel accountTypeRadio;
    private JLabel accountTypeLabel;

    private boolean isRegister;

    public LoginMenu(boolean isRegister, LoginFormActions formActions) {
        this.formActions = formActions;
        this.isRegister = isRegister;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.accountTypeRadio.setVisible(isRegister);
        this.accountTypeLabel.setVisible(isRegister);
    }

    private void onOK() {
        // add your code here
        try {
            if (this.userNameField.getText().isEmpty()) throw new Exception("Please fill in username");
            if (this.passwordField.getPassword().length > 0) throw new Exception("Please fill in password");

            this.formActions.onOk(
                    this.userNameField.getText(),
                    String.valueOf(this.passwordField.getPassword()),
                    this.isBuyer.isSelected() ? Login.UserType.Buyer : Login.UserType.Seller,
                    this.isRegister
            );
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        try {
            this.formActions.onCancel();
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
