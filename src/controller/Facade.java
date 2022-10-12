package controller;

import util.Files;
import models.ClassProductList;
import models.Person;
import models.Product;
import models.UserInformation;
import util.ProductIterator;
import util.Utils;

import java.util.Scanner;

public class Facade {
    /**
     * The type of the user: Buyer: 0, Seller: 1
     */
    private Login.UserType UserType;

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
        try {
            Scanner io = new Scanner(System.in);
            System.out.println("Username: ");
            String userName = io.nextLine();
            System.out.println("Password: ");
            String password = io.nextLine();
            var loginData = Login.GetInstance().userLogin(userName, password);
            this.thePerson = loginData.person;
            this.UserType = loginData.userType;
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
     * @param userInfoItem info about user to create.
     */
    public void createUser(UserInformation userInfoItem) {
        try {
            Login.GetInstance().addNewUser(
                    userInfoItem.userName,
                    userInfoItem.password,
                    userInfoItem.userType,
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
                Product newProduct = new Product(parts[1], parts[0]);
                theProductList.add(newProduct);
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
            assert thePerson != null : "Person not known";
            var userProductInfoPairs = Utils.GetPairs(userProductInfo);

            for (var parts : userProductInfoPairs) {
                if (thePerson.getName() == parts[0]) {
                    ProductIterator it = new ProductIterator(theProductList);
                    Product associatedProduct = null;
                    while (it.hasNext()) {
                        Product next = it.Next();
                        if (next.getName() == parts[1]) {
                            associatedProduct = next;
                            break;
                        }
                    }
                    assert associatedProduct != null : "No matching product found";
                    thePerson.addAssociatedProduct(associatedProduct);
                }
            }

        } catch (Exception e) {
            System.out.println("Error in reading user product list.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays product list in a dialog.
     */
    public void selectProduct() {}

    /**
     * calls a menu creator as per usertype.
     * @call CreateProductMenu
     */
    public void productOperation() {}
}
