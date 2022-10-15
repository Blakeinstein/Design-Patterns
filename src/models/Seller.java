package models;

import view.NewProductMenu;
import view.ProductMenu;

public class Seller extends Person{

    public Seller(String userName) {
        super(userName);
    }

    /**
     * Show appropriate items on the menu as per the need
     * of the buyer.
     */
    @Override
    public void showMenu() {

    }

    public ProductMenu CreateProductMenu(NewProductMenu.NewProductHandler handler) {
        return NewProductMenu.CreateProductDialog(handler);
    }
}
