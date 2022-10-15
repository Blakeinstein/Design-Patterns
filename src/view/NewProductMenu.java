package view;

import models.ClassProductList;
import models.Product;
import util.ProductIterator;

import javax.swing.*;
import java.awt.event.*;

public class NewProductMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public NewProductMenu() {
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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static ProductMenu ShowCreateProductDialog() {
        Object[] opts = {
                "Meat",
                "Produce"
        };
        int selection = JOptionPane.showOptionDialog(
                AppView.Get().getFrame(),
                "What kind of product would you like?",
                "Create a new product",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opts,
                opts[0]
        );

        NewProductMenu dialog = new NewProductMenu();
        dialog.pack();

        switch (selection) {
            case 0:
                return new MeatProductMenu(dialog);
            case 1:
            default:
                return new ProduceProductMenu(dialog);
        }
    }

    public void showMenu(ClassProductList productList, Product.PRODUCT_TYPE productType) {
        var it = new ProductIterator(productList);
        var model = new DefaultComboBoxModel<String>();
        while (it.hasNext()) {
            var next = it.Next();
            if (next.getType() == Product.PRODUCT_TYPE.Produce) {
                model.addElement(next.getName());
            }
        }
        this.pack();
        this.setVisible(true);
    }
}
