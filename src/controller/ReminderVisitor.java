package controller;

import models.Product;
import models.Trading;

/**
 * Implementation of NodeVisitor with concrete logic.
 */
public class ReminderVisitor extends NodeVisitor{
    /**
     * Visit a product, and in turn visit each trading in
     * the product.
     * @param product Product to visit.
     */
    public void visitProduct(Product product) {
        product.accept(this);
    }

    /**
     * Visit a Trading, compare the current date and the
     * due date of the trading and show the proper
     * reminding information on the reminder.
     * @param trading Trading to visit.
     */
    public void visitTrading(Trading trading) {
        trading.accept(this);
    }

    /**
     * Visit a facade, and in turn visit each product in
     * the facade's product list.
     * @param facade Facade to visit.
     */
    public void visitFacade(Facade facade) {
        facade.accept(this);
    }
}
