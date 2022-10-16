package models;

import view.NewProductMenu;
import view.ProductMenu;

/**
 * An abstract idea of a person. Implements the Bridge design pattern.
 */
public abstract class Person {
    /**
     * Name of the person.
     */
    private final String name;

    /**
     * List of associated products.
     */
    private final ClassProductList associatedProducts;

    /**
     * Points to a concrete ProductMenu object.
     */
    public ProductMenu theProductMenu;

    public Person(String userName) {
        name = userName;
        this.associatedProducts = new ClassProductList();
    }

    /**
     * Get name of person.
     * @return name of person.
     */
    public String getName() {
        return name;
    }

    /**
     * Associate a product to the person
     * @param product product to associate.
     */
    public void addAssociatedProduct(Product product) {
        this.associatedProducts.add(product);
    }

    /**
     * Overridden by a class to show the menu.
     */
    public abstract void showMenu();

    /**
     * Calls implementation to show the add buttons.
     */
    public void showAddButton() {}

    /**
     * Calls implementation to show the view buttons.
     */
    public void showViewButton() {}

    /**
     * Calls implementation to show the radio buttons.
     */
    public void showRadioButton() {}

    /**
     * Calls implementation to show the labels.
     */
    public void showLabels() {}

    /**
     * Abstract function for creating product menu for a person
     * @param handler the handler for the product menu
     * @return instance of a product menu
     */

    public abstract ProductMenu CreateProductMenu(NewProductMenu.NewProductHandler handler);

    /**
     * Gets the associated products with the person
     * @return a product list
     */
    public ClassProductList getAssociatedProducts() {
        return this.associatedProducts;
    }

    /**
     * Clears associated products with the user.
     */
    public void resetAssociatedProducts() {
        this.associatedProducts.clear();
    }
}
