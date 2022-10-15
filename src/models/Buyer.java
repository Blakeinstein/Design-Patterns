package models;

import view.NewProductMenu;
import view.ProductMenu;

public class Buyer extends Person{

    public Buyer(String userName) {
        super(userName);
    }

    /**
     * Show appropriate items on the menu as per the need
     * of the buyer.
     */
    public void showMenu() {

    }

    public ProductMenu CreateProductMenu() {
        return NewProductMenu.ShowCreateProductDialog();
    }
}
