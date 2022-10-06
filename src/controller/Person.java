package controller;

import view.ProductMenu;

public abstract class Person {

    /**
     * Points to a concrete ProductMenu object.
     */
    private ProductMenu theProductMenu;

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
