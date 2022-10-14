package controller;

import util.Files;
import models.ClassProductList;
import models.Person;
import models.Product;
import models.UserInformation;
import util.ProductIterator;
import util.Utils;
import view.AppView;
import view.TradingMenu;

import javax.swing.*;

public class Facade {
    /**
     * The type of the user: Buyer: 0, Seller: 1
     */
    private Login.USER_TYPE UserType;

    /**
     * The object that holds the currently selected product.
     */
    private Product theSelectProduct;

    /**
     * The selected product category: Meat: 0, Produce: 1.
     */
    private Product.PRODUCT_TYPE nProductCategory;

    /**
     * The list of products of the entire system
     */
    private ClassProductList theProductList;

    /**
     * The current user
     */
    private Person thePerson;

    public Facade() {
        this.createProductList();
    }

    /**
     * Show the login Gui
     * @return the login result.
     */
    public boolean login() {
        try {
            var user = Login.GetInstance().userLogin();
            if (user == null) return false; // the user pressed cancel.
            this.thePerson = user.person;
            this.UserType = user.USERTYPE;
            this.attachProductToUser();
            return true;
        } catch (Exception e) {
            System.out.println("Error in login");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new trade and fills in the required information.
     * It does not update the product menu, the product menu needs to
     * be refreshed separately.
     * @calls SellerTradingMenu or BuyerTradingMenu based on
     * type of user.
     */
    public void addTrading() {
        var availableList = new ClassProductList();
        var userList = this.thePerson.getAssociatedProducts();
        var it = new ProductIterator(this.theProductList);
        while (it.hasNext()) {
            var next = it.Next();
            if (!userList.contains(next)) {
                availableList.add(next);
            }
        }
        if (availableList.size() == 0) {
            JOptionPane.showMessageDialog(AppView.Get().getFrame(), "No more products to associate");
            return;
        }
        var dialog = new TradingMenu(
                this.UserType,
                availableList,
                new TradingMenu.TradingMenuActions() {
                    public void onOk(Product selectedProduct) throws Exception {
                        Facade.this.thePerson.addAssociatedProduct(selectedProduct);
                        Files.WriteLineToFile(
                                "UserProduct.txt",
                                String.format("%s:%s", Facade.this.thePerson.getName(), selectedProduct.getName())
                        );
                    }
                }
        );
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

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
     * @param userInfoItem info about user to create.
     */
    public void createUser(UserInformation userInfoItem) {
        try {
            Login.GetInstance().addNewUser(
                    userInfoItem.userName,
                    userInfoItem.password,
                    userInfoItem.USERTYPE,
                    true
            );
        } catch (Exception e) {
            System.out.println("Error creating user");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates the product list of the entire system.
     */
    public void createProductList() {
        theProductList = new ClassProductList();
        String productInfo = Files.GetProductInfo();

        try {
            var productInfoPairs = Utils.GetPairs(productInfo);
            for (var parts : productInfoPairs) {
                this.theProductList.add(new Product(parts[1], parts[0]));
            }
        } catch (Exception e) {
            System.out.println("Error in reading product list.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads the UserProduct.txt file to create a productList
     * attached to a user.
     */
    public void attachProductToUser() {
        String userProductInfo = Files.GetUserProductInfo();
        try {
            if (this.thePerson == null) throw new Exception("Person not known");
            this.thePerson.resetAssociatedProducts();
            var userProductInfoPairs = Utils.GetPairs(userProductInfo);
            var name = this.getLoggedInUserName();

            for (var parts : userProductInfoPairs) {
                if (name.equals(parts[0])) {
                    ProductIterator it = new ProductIterator(theProductList);
                    Product associatedProduct = null;
                    while (it.hasNext()) {
                        Product next = it.Next();
                        if (next.getName().equals(parts[1])) {
                            associatedProduct = next;
                            break;
                        }
                    }
                    if (associatedProduct == null)
                        throw new Exception(
                                String.format("No matching product found with name %s in available product list.", parts[1])
                        );
                    thePerson.addAssociatedProduct(associatedProduct);
                }
            }
            AppView.Get().SetProductList(this.thePerson.getAssociatedProducts());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    e.getMessage(),
                    "Error in reading user product list.",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Displays product list in a dialog.
     */
    public void selectProduct(String productName) throws Exception{
        ProductIterator it = new ProductIterator(
                this.thePerson.getAssociatedProducts()
        );
        while(it.hasNext()) {
            var next = it.Next();
            if (next.getName().equals(productName)) {
                this.theSelectProduct = next;
                this.nProductCategory = next.getType();
                return;
            }
        }
        throw new Exception("Invalid product selected");
    }

    /**
     * calls a menu creator as per usertype.
     * @call CreateProductMenu
     */
    public void productOperation() {}

    public boolean logout() {
        this.UserType = null;
        this.thePerson = null;
        return false;
    }

    public String getLoggedInUserName() {
      return this.thePerson.getName();
    }
}
