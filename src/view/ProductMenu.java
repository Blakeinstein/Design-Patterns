package view;

/**
 * Abstract interface for Product menus
 * Implements the bridge design pattern.
 */
public interface ProductMenu {

    void showMenu();

    /**
     * To show the add buttons.
     */
    void showAddButton();

    /**
     * To show the view buttons.
     */
    void showViewButton();

    /**
     * To show the radio buttons.
     */
    void showRadioButton();

    /**
     * To show the labels.
     */
    void showLabels();

    void showComboxes();
}
