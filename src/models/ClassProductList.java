package models;

import controller.NodeVisitor;
import util.ProductIterator;

import java.util.ArrayList;

/**
 * A list class of products. Implemented the Iterator design pattern
 */
public class ClassProductList extends ArrayList<Product> {

    /**
     * Accepts a visitor and passes it to the products.
     * @param visitor a visitor instance
     */
    public void accept(NodeVisitor visitor) {
        var it = new ProductIterator(this);
        while (it.hasNext()) {
            visitor.visitProduct(it.Next());
        }
    }
}
