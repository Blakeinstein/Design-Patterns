package controller;

import models.*;
import util.Files;
import util.OfferingIterator;
import util.ProductIterator;
import util.Utils;
import view.*;
import view.Reminder;

import javax.swing.*;
import java.util.ArrayList;

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

    private final OfferingList offeringList;

    private final ArrayList<Trading> tradingList;

    /**
     * The current user
     */
    private Person thePerson;

    public Facade() {
        this.createProductList();
        this.offeringList = new OfferingList();
        this.tradingList = new ArrayList<>();
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
     */
    public void addTrading() {
        if (this.theSelectProduct == null) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    "No product selected",
                    "Error adding trading",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        for (var t : this.tradingList) {
            if (t.getProduct() == this.theSelectProduct) {
                JOptionPane.showMessageDialog(
                        AppView.Get().getFrame(),
                        "Product already marked as trading.",
                        "Error adding trading",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }
        this.tradingList.add(
                new Trading(
                        this.theSelectProduct,
                        this.thePerson
                )
        );
        JOptionPane.showMessageDialog(
                AppView.Get().getFrame(),
                String.format(
                        "Product %s of type %s successfully marked as trading.",
                        this.theSelectProduct.getName(),
                        this.nProductCategory == Product.PRODUCT_TYPE.Meat ? "Meat" : "Produce"
                ),
                String.format("Successfully marked offering for %s", this.theSelectProduct.getName()),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Views the trading information.
     */
    public void viewTrading() {
        if (this.tradingList.size() == 0) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    "No tradings to show",
                    "Error viewing trading",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            var dialog = new TradingMenu(
                    this.UserType,
                    this.tradingList
            );
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    /**
     * View the given offering.
     */
    public void viewOffering() {
        if (this.offeringList.size() == 0) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    "No offerings to show",
                    "Error viewing offering",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            var dialog = new OfferingMenu(
                    this.UserType,
                    this.offeringList
            );
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    /**
     * Set the deal flag of a given offering.
     */
    public void markOffering() {
        if (this.theSelectProduct == null) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    "No product selected",
                    "Error marking offering",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            this.offeringList.add(
                    new Offering(this.theSelectProduct, this.thePerson)
            );
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    String.format("Marked offering for %s", this.theSelectProduct.getName()),
                    String.format("New offering: %s", this.theSelectProduct.getName()),
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Used to submit the offering.
     */
    public void submitOffering() {
        var count = 0;
        var it = new OfferingIterator(this.offeringList);
        while (it.hasNext()) {
            var next = it.Next();
            if (next.submit()) count++;
        }
        if (count == 0) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    "No products marked as offering",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    String.format("%d products submitted for offering", count),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * shows the reminder box to remind buyer of the
     * upcoming overdue trading window.
     */
    public void remind() {
        new ReminderVisitor();
        new Reminder();
    }

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
     */
    public void productOperation() {
        var productMenu = this.thePerson.CreateProductMenu(
                new NewProductMenu.NewProductHandler() {
                    public void onOk(String productName, Product.PRODUCT_TYPE type, boolean associate) throws Exception {
                        var it = new ProductIterator(Facade.this.theProductList);
                        while (it.hasNext()) {
                            if (it.Next().getName().equals(productName)) {
                                throw new Exception(String.format("Product with name %s already exists", productName));
                            }
                        }
                        var product = new Product(
                                productName,
                                type
                        );
                        Facade.this.theProductList.add(product);
                        Files.WriteLineToFile(
                                "ProductInfo.txt",
                                String.format("%s:%s", type == Product.PRODUCT_TYPE.Meat ? "Meat" : "Produce", product.getName())
                        );
                        if (associate) {
                            Facade.this.thePerson.addAssociatedProduct(product);
                            Files.WriteLineToFile(
                                    "UserProduct.txt",
                                    String.format("%s:%s", Facade.this.thePerson.getName(), product.getName())
                            );
                        }
                    }
                }
        );
        if (productMenu != null) {
            this.thePerson.showMenu();
        }
    }

    public boolean logout() {
        this.UserType = null;
        this.thePerson = null;
        this.tradingList.clear();
        this.offeringList.clear();
        return false;
    }

    public String getLoggedInUserName() {
      return this.thePerson.getName();
    }
}
