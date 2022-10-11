package models;

import view.ProductMenu;

public class Buyer extends Person{

    public Buyer(String userName) {
        super(userName);
    }

    /**
     * Show appropriate items on the menu as per the need
     * of the buyer.
     */
    @Override
    public void showMenu() {

    }

    @Override
    public ProductMenu CreateProductMenu() {
        return null;
    }
}
