package controller;

import models.Product;
import models.Trading;

/**
 * Abstract class of the visitor which can visit a Facade,
 * a Trading or a Product. Implements the visitor design pattern.
 */
public abstract class NodeVisitor {

    /**
     * Visit a product.
     * @param product Product to visit.
     */
    public abstract void visitProduct(Product product);

    /**
     * Visit a Trading.
     * @param trading Trading to visit.
     */
    public abstract void visitTrading(Trading trading);

    /**
     * Visit a facade.
     * @param facade Facade to visit.
     */
    public abstract void visitFacade(Facade facade);
}
