package view;

import controller.Facade;
import models.ClassProductList;

public interface ProductMenu {

    public void showMenu(ClassProductList productList);

    /**
     * To show the add buttons.
     */
    public void showAddButton();

    /**
     * To show the view buttons.
     */
    public void showViewButton();

    /**
     * To show the radio buttons.
     */
    public void showRadioButton();

    /**
     * To show the labels.
     */
    public void showLabels();

    public void showComboxes();
}
