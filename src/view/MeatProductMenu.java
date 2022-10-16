package view;

import models.Product;

/** Product menu for meat products
 * Implements the bridge design pattern.
 */
public class MeatProductMenu implements ProductMenu {

    private final NewProductMenu dialog;

    public MeatProductMenu(NewProductMenu dialog) {
        this.dialog = dialog;
    }

    /**
     */
    public void showMenu() {
        this.dialog.showMenu(Product.PRODUCT_TYPE.Meat);
    }

    /**
     * To show the add buttons.
     */
    @Override
    public void showAddButton() {

    }

    /**
     * To show the view buttons.
     */
    @Override
    public void showViewButton() {

    }

    /**
     * To show the radio buttons.
     */
    @Override
    public void showRadioButton() {

    }

    /**
     * To show the labels.
     */
    @Override
    public void showLabels() {

    }

    @Override
    public void showComboxes() {

    }
}
