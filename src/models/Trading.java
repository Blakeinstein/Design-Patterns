package models;

import controller.NodeVisitor;

import java.util.Date;

/**
 * Class for a trading.
 */
public class Trading {
    /**
     * Product involved in the trading
     */
    private final Product product;
    /**
     * Owner of the trading
     */
    private final Person person;
    /**
     * Due date of the trading
     */
    private final Date due;


    public Trading(Product product, Person person, Date due) {
        this.person = person;
        this.product = product;
        this.due = due;
    }

    /**
     * Accepts an instance of a node visitor, returns if the current trading
     * is still pending
     * @param visitor an instance of a node visitor
     * @return true if the current trading is still pending
     */
    public boolean accept(NodeVisitor visitor) {
        return this.due.getTime() > (new Date()).getTime();
    }

    /**
     * Get the product involved in the trading
     * @return product in the trading
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Get the person involved in the trading
     * @return person in the trading
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Get the due date of the trading
     * @return due date of the trading
     */
    public Date getDueDate() {
        return due;
    }
}
