package view;

import controller.Login;
import models.ClassProductList;
import models.Product;
import util.ProductIterator;

import javax.swing.*;
import java.awt.event.*;

public class TradingMenu extends JDialog {
    public static class TradingMenuActions {
        public void onOk(Product selectedProduct) throws Exception{};
        public void onCancel() throws Exception {};
    }
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox selectedProduct;

    private final TradingMenuActions formActions;

    private final ClassProductList productList;

    public TradingMenu(Login.USER_TYPE userType, ClassProductList productList, TradingMenuActions formActions) {
        this.formActions = formActions;
        this.productList = productList;
        setContentPane(contentPane);
        setModal(true);
        setTitle(String.format("Add %s prod", userType == Login.USER_TYPE.Buyer ? "Buyer" : "Seller"));
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

        var model = new DefaultComboBoxModel<String>();
        var it = new ProductIterator(this.productList);
        while (it.hasNext()) {
            model.addElement(it.Next().getName());
        }
        this.selectedProduct.setModel(model);
    }

    private void onOK() {
        // add your code here
        try {
            var idx = this.selectedProduct.getSelectedIndex();
            if (idx < 0 || idx >= this.productList.size()) {
                throw new Exception("Select a product");
            }
            this.formActions.onOk(
                    this.productList.get(idx)
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
