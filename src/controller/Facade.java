package controller;

import models.*;
import util.Files;
import util.OfferingIterator;
import util.ProductIterator;
import util.Utils;
import view.*;

import javax.swing.*;
import java.util.Date;

/**
 * Controller class for the entire app.
 * Implements the Facade design pattern
 */
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

    /**
     * New Offerings
     */
    private int newOfferings = 0;

    /**
     * Public constructor
     */
    public Facade() {
        this.createProductList();
        this.attachProductToUser();
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
            AppView.Get().SetProductList(this.theProductList);
            this.thePerson.showMenu(this);
            this.newOfferings = 0;
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
            return;
        }
        for (var t : this.theSelectProduct.getTradings()) {
            if (t.getPerson().getName().equals(this.thePerson.getName())) {
                JOptionPane.showMessageDialog(
                        AppView.Get().getFrame(),
                        "A trading already exists for this product",
                        "Error adding trading",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }
        var dialog = new TradingMenu(
                this.UserType,
                this.theSelectProduct.getName(),
                new TradingMenu.TradingMenuActions() {
                    public void onOk(Date d) throws Exception {
                        var trading = new Trading(
                                Facade.this.theSelectProduct,
                                Facade.this.thePerson,
                                d
                        );
                        Facade.this.theSelectProduct.addTrading(trading);
                        Files.WriteLineToFile(
                                "UserProduct.txt",
                                String.format("%s:%s", trading.getPerson().getName(), trading.getProduct().getName())
                        );
                        JOptionPane.showMessageDialog(
                                AppView.Get().getFrame(),
                                String.format(
                                        "Product %s of type %s successfully marked as trading due on %s",
                                        Facade.this.theSelectProduct.getName(),
                                        Facade.this.nProductCategory == Product.PRODUCT_TYPE.Meat ? "Meat" : "Produce",
                                        Utils.getDateFormatter().format(d)
                                ),
                                String.format("Successfully marked offering for %s", Facade.this.theSelectProduct.getName()),
                                JOptionPane.INFORMATION_MESSAGE
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
     */
    public void viewTrading() {
        if (this.theSelectProduct == null) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    "No product selected",
                    "Error viewing tradings",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            var tradings = this.theSelectProduct.getTradings();
            if (tradings.size() == 0) {
                JOptionPane.showMessageDialog(
                        AppView.Get().getFrame(),
                        "No tradings for selected product",
                        "Error viewing tradings",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            var sb = new StringBuilder();
            for (var t : tradings) {
                sb.append(
                        String.format(
                                "Product: %s, Buyer: %s, Due: %s",
                                t.getProduct().getName(),
                                t.getPerson().getName(),
                                Utils.getDateFormatter().format(t.getDueDate())
                        )
                ).append("\n");
            }
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    sb.toString(),
                    String.format(
                            "Tradings for %s, %s",
                            this.UserType == Login.USER_TYPE.Seller ? "Seller" : "Buyer",
                            this.thePerson.getName()
                    ),
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * View the given offering.
     */
    public void viewOffering() {
        var offerings = this.theSelectProduct.getOfferingList();
        if (offerings.size() == 0) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    String.format("No offering to show for %s", this.theSelectProduct.getName()),
                    "Error viewing offering",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            var dialog = new OfferingMenu(
                    this.UserType,
                    offerings
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
            try {
                this.theSelectProduct.addOffering(this.thePerson);
                this.newOfferings++;
                JOptionPane.showMessageDialog(
                        AppView.Get().getFrame(),
                        String.format("Marked offering for %s", this.theSelectProduct.getName()),
                        String.format("New offering: %s", this.theSelectProduct.getName()),
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        AppView.Get().getFrame(),
                        "You already have an offering for the product",
                        "Error marking offering",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }
    }

    /**
     * Used to submit the offering.
     */
    public void submitOffering() {
        if (this.newOfferings == 0) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    "No products marked as offering",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        try {
            var it = new ProductIterator(this.theProductList);
            var sb = new StringBuilder();
            while (it.hasNext()) {
                var p = it.Next();
                var it2 = new OfferingIterator(p.getOfferingList());
                while (it2.hasNext()) {
                    var next = it2.Next();
                    if (next.submit()) {
                        sb.append(
                                String.format("%s:%s", this.thePerson.getName(), p.getName())
                        ).append("\n");
                    }
                }
            }
            Files.WriteLineToFile("UserProduct.txt", sb.toString());
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    String.format("%d products submitted for offering", this.newOfferings),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            this.newOfferings = 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * shows the reminder box to remind buyer of the
     * upcoming overdue trading window.
     */
    public void remind() {
        var reminder = new Reminder();
        var visitor = new ReminderVisitor(reminder);
        visitor.visitFacade(this);
        var dialog = new ReminderView(reminder);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
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
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    e.getMessage(),
                    "Error creating user",
                    JOptionPane.ERROR_MESSAGE
            );
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
            JOptionPane.showMessageDialog(
                    AppView.Get().getFrame(),
                    e.getMessage(),
                    "Error in reading product list.",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Reads the UserProduct.txt file to create a productList
     * attached to a user.
     */
    public void attachProductToUser() {
        String userProductInfo = Files.GetUserProductInfo();
        try {
            var people = Login.GetInstance().getUsers();
            var userProductInfoPairs = Utils.GetPairs(userProductInfo);
            for (var parts : userProductInfoPairs) {
                if (!people.containsKey(parts[0])) {
                    throw new Exception(
                            String.format("Unknown user %s in UserProduct.txt", parts[0])
                    );
                }
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
                var p = people.get(parts[0]);
                switch (p.USERTYPE) {
                    case Buyer -> associatedProduct.addTrading(
                        new Trading(
                                associatedProduct,
                                p.person,
                                null
                        )
                    );
                    case Seller -> associatedProduct.addOffering(p.person).submit();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Error in reading user product list.",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Displays product list in a dialog.
     */
    public void selectProduct(String productName) {
        ProductIterator it = new ProductIterator(
                this.theProductList
        );
        while(it.hasNext()) {
            var next = it.Next();
            if (next.getName().equals(productName)) {
                this.theSelectProduct = next;
                this.nProductCategory = next.getType();
                return;
            }
        }
        this.theSelectProduct = null;
        this.nProductCategory= null;
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
                            Facade.this.theSelectProduct.addOffering(Facade.this.thePerson);
                        }
                    }
                }
        );
        if (productMenu != null) {
            productMenu.showMenu();
        }
    }

    /**
     * Logs the user out
     * @return CONST false for compatibility with login
     */
    public boolean logout() {
        this.UserType = null;
        this.thePerson = null;
        return false;
    }

    /**
     * Gets the username of the logged-in user
     * @return name of the user
     */
    public String getLoggedInUserName() {
      return this.thePerson.getName();
    }

    /**
     * accepts a node visitor, and passes it to the product list
     * @param visitor the incoming visitor
     */
    public void accept(NodeVisitor visitor) {
       this.theProductList.accept(visitor);
    }
}
