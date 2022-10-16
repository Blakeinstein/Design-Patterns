package view;

import controller.Login;
import util.Utils;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TradingMenu extends JDialog {

    public static class TradingMenuActions {
        public void onOk(Date d) throws Exception {}
        public void onCancel() throws Exception {}
    }
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dueDate;
    private JTextField dueTime;


    private final TradingMenuActions handler;

    public TradingMenu(Login.USER_TYPE userType, TradingMenuActions handler) {
        this.handler = handler;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(String.format("%s tradings", userType == Login.USER_TYPE.Buyer ? "Buyer" : "Seller"));

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            var s = this.dueDate.getText();
            if (s.isEmpty()) throw new Exception("Please input date in the format dd-M-yyyy");
            var t = this.dueTime.getText();
            if (t.isEmpty()) t = "00:00:00";
            Date date = null;
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

    private void onCancel() {
        try {
            this.handler.onCancel();
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    getRootPane(),
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
