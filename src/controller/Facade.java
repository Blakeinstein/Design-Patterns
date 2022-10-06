package controller;

import model.ClassProductList;
import model.Product;
import model.UserInformation;

public class Facade {
    /**
     * The type of the user: Buyer: 0, Seller: 1
     */
    private int UserType;

    /**
     * The object that holds the currently selected product.
     */
    private Product theSelectProduct;

    /**
     * The selected product category: Meat: 0, Produce: 1.
     */
    private int nProductCategory;

    /**
     * The list of products of the entire system
     */
    private ClassProductList theProductList;

    /**
     * The current user
     */
    private Person thePerson;

    /**
     * Show the login Gui
     * @return the login result.
     */
    public boolean login() {
        return false;
    }

    /**
     * Adds a new trade and fills in the required information.
     * It does not update the product menu, the product menu needs to
     * be refreshed separately.
     * @calls SellerTradingMenu or BuyerTradingMenu based on
     * type of user.
     */
    public void addTrading() {}

    /**
     * Views the trading information.
     * @calls SellerTradingMenu or BuyerTradingMenu based on
     * type of user.
     */
    public void viewTrading() {}

    /**
     * View the given offering.
     */
    public void viewOffering() {};

    /**
     * Set the deal flag of a given offering.
     */
    public void markOffering() {};

    /**
     * Used to submit the offering.
     */
    public void submitOffering() {};

    /**
     * shows the reminder box to remind buyer of the
     * upcoming overdue trading window.
     */
    public void remind() {}

    /**
     * Creates a user Object
     * @param userinfoitem info about user to create.
     */
    public void createUser(UserInformation userinfoitem) {}

    /**
     * Creates the product list of the entire system.
     */
    public void createProductList() {}

    /**
     * Reads the UserProduct.txt file to create a productList
     * attached to a user.
     */
    public void attachProductToUser() {}

    /**
     * Displays product list in a dialog.
     * @return selected Product
     */
    public void selectProduct() {}

    /**
     * calls a menu creator as per usertype.
     * @call CreateProductMenu
     */
    public void productOperation() {}
}
