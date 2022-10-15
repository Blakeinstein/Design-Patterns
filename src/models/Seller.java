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
    public void showMenu() {
        this.theProductMenu.showMenu();
    }

    public ProductMenu CreateProductMenu(NewProductMenu.NewProductHandler handler) {
        this.theProductMenu = NewProductMenu.CreateProductDialog(handler);
        return this.theProductMenu;
    }
}
