package models;

import view.NewProductMenu;
import view.ProductMenu;

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
     */
    public void showMenu() {
        this.theProductMenu.showMenu();
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
