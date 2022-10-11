package models;

import models.ClassProductList;
import models.Product;
import view.ProductMenu;

public abstract class Person {
    /**
     * Name of the person.
     */
    private String name;

    /**
     * List of associated products.
     */
    private ClassProductList associatedProducts;

    /**
     * Points to a concrete ProductMenu object.
     */
    private ProductMenu theProductMenu;

    public Person(String userName) {
        name = userName;
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
        associatedProducts.add(product);
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

    public abstract ProductMenu CreateProductMenu();
}