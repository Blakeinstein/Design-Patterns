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
    @Override
    public void visitProduct(Product product) {

    }

    /**
     * Visit a Trading, compare the current date and the
     * due date of the trading and show the proper
     * reminding information on the reminder.
     * @param trading Trading to visit.
     */
    @Override
    public void visitTrading(Trading trading) {

    }

    /**
     * Visit a facade, and in turn visit each product in
     * the facade's product list.
     * @param facade Facade to visit.
     */
    @Override
    public void visitFacade(Facade facade) {

    }
}
