package view;

import models.Product;

import javax.swing.*;
import java.awt.event.*;

public class NewProductMenu extends JDialog {
    public static class NewProductHandler {
        public void onOk(String productName, Product.PRODUCT_TYPE type, boolean associate) throws Exception {};
        public void onCancel() throws Exception {};
    }
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox associate;
    private JTextField productName;

    private Product.PRODUCT_TYPE productType;

    private final NewProductHandler handler;

    public NewProductMenu(NewProductHandler handler) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        this.handler = handler;
    }

    private void onOK() {
        try {
            if (this.productName.getText().isEmpty()) throw new Exception("Enter a product name");
            this.handler.onOk(
                    this.productName.getText(),
                    this.productType,
                    this.associate.isSelected()
            );
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

    public static ProductMenu CreateProductDialog(NewProductHandler handler) {
        Object[] opts = {
                "Meat",
                "Produce"
        };
        String selection = (String)JOptionPane.showInputDialog(
                AppView.Get().getFrame(),
                "What kind of product would you like?",
                "Create a new product",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opts,
                opts[0]
        );

        NewProductMenu dialog = new NewProductMenu(handler);

        if (selection == null) return null;
        switch (selection) {
            case "Meat":
                return new MeatProductMenu(dialog);
            case "Produce":
                return new ProduceProductMenu(dialog);
            default:
                return null;
        }
    }

    public void showMenu(Product.PRODUCT_TYPE productType) {
        this.productType = productType;
        this.setTitle(
                String.format("New %s product", productType == Product.PRODUCT_TYPE.Meat ? "Meat" : "Produce")
        );
        this.setLocationRelativeTo(AppView.Get().getFrame());
        this.pack();
        this.setVisible(true);
    }
}
