package models;

import controller.Facade;
import view.AppView;
import view.NewProductMenu;
import view.ProductMenu;

import javax.swing.*;

/**
 * Class for buyer.
 */
public class Buyer extends Person{


    public Buyer(String userName) {
        super(userName);
    }

    /**
     * Show appropriate items on the menu as per the need
     * of the buyer.
     */
    public void showMenu(Facade facade) {
        var c = AppView.Get().getToolboxButtons();
        var b1 = c.get(0);
        var b2 = c.get(1);
        var b3 = c.get(2);

        b1.setText("Add Trading");
        b1.setToolTipText("Add trading for the selected item");
        b1.addActionListener(e -> facade.addTrading());
        b1.setVisible(true);

        b2.setText("View Offerings");
        b2.setToolTipText("View offerings for the selected Item");
        b2.addActionListener(e -> facade.viewOffering());
        b2.setVisible(true);

        b3.setVisible(false);
    }

    /**
     * Factory method to create appropriate product menu, implements the factory design pattern.
     * @param handler the handler for the product menu
     * @return an instance of a product menu
     */
    public ProductMenu CreateProductMenu(NewProductMenu.NewProductHandler handler) {
        this.theProductMenu = NewProductMenu.CreateProductDialog(handler);
        return this.theProductMenu;
    }
}
