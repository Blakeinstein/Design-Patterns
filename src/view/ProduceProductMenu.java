package view;

import models.ClassProductList;
import models.Product;
import util.ProductIterator;

public class ProduceProductMenu implements ProductMenu {

    private final NewProductMenu dialog;

    public ProduceProductMenu(NewProductMenu dialog) {
        this.dialog = dialog;
    }

    /**
     * @param productList
     */
    public void showMenu(ClassProductList productList) {
        this.dialog.showMenu(productList, Product.PRODUCT_TYPE.Produce);
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
