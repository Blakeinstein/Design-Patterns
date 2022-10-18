package view;

import controller.Login;
import util.Utils;

import javax.swing.*;
import java.awt.event.*;
import java.util.Date;

public class TradingMenu extends JDialog {

    public static class TradingMenuActions {
        public void onOk(Date d) throws Exception {}
    }
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dueDate;
    private JTextField dueTime;


    private final TradingMenuActions handler;

    public TradingMenu(Login.USER_TYPE userType, String productName, TradingMenuActions handler) {
        this.handler = handler;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(String.format("%s trading for %s", userType == Login.USER_TYPE.Buyer ? "Buyer" : "Seller", productName));

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> dispose());

        // call dispose() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call dispose() on ESCAPE
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            var s = this.dueDate.getText();
            if (s.isEmpty()) throw new Exception("Please input date in the format dd-M-yyyy");
            var t = this.dueTime.getText();
            if (t.isEmpty()) t = "00:00:00";
            Date date;
            try {
                date = Utils.getDateFormatter().parse(
                        String.format("%s %s", s, t)
                );
            } catch (Exception e) {
                throw new Exception("Error parsing date, use the format dd-M-yyyy for date and HH:mm:ss for time.\n Ex: 22-01-2015 16:15:55");
            }
            if (date == null) return;
            this.handler.onOk(date);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    getRootPane(),
                    e.getMessage(),
                    "Error creating product",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
