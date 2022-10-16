package view;

import controller.Login;
import models.Trading;
import util.ProductIterator;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TradingMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JList tradingList;

    public TradingMenu(Login.USER_TYPE userType, ArrayList<Trading> tradings) {
        setContentPane(contentPane);
        setModal(true);
        setTitle(String.format("%s tradings", userType == Login.USER_TYPE.Buyer ? "Buyer" : "Seller"));
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onOK() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        var model = new DefaultListModel<String>();
        for (var t : tradings) {
            model.addElement(
                    String.format("%s - %s", t.getProduct().getName(), t.getPerson().getName())
            );
        }
        this.tradingList.setModel(model);
    }

    private void onOK() {
        dispose();
    }
}
