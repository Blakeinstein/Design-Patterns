package models;

import controller.Facade;
import view.AppView;
import view.NewProductMenu;
import view.ProductMenu;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class for seller.
 */
public class Seller extends Person{

    public Seller(String userName) {
        super(userName);
    }

    /**
     * Show appropriate items on the menu as per the need
     * of the seller.
     * @param facade
     */
    public void showMenu(Facade facade) {
        var c = AppView.Get().getToolboxButtons();
        var b1 = c.get(0);
        var b2 = c.get(1);
        var b3 = c.get(2);

        b1.setText("Add offering");
        b1.setToolTipText("Add offering for selected item");
        b1.addActionListener(e -> facade.markOffering());
        b1.setVisible(true);

        b2.setText("View trading");
        b2.setToolTipText("View tradings for selected item");
        b2.addActionListener(e -> facade.viewTrading());
        b2.setVisible(true);

        b3.setText("Submit offerings");
        b3.setToolTipText("Submit all added offerings");
        b3.addActionListener(e -> facade.submitOffering());
        b3.setVisible(true);
    }

    /**
     * Factory method to create appropriate product menu, implements the factory design pattern.
     * @param handler the handler for the product menu
     * @return an instance of a product menu
     */
    public ProductMenu CreateProductMenu(NewProductMenu.NewProductHandler handler) {
        this.theProductMenu = NewProductMenu.CreateProductDialog(handler, true);
        return this.theProductMenu;
    }
}
