package models;

import controller.NodeVisitor;
import util.ProductIterator;

import java.util.ArrayList;

/**
 * A list class of products.
 */
public class ClassProductList extends ArrayList<Product> {
    public void accept(NodeVisitor visitor) {
        var it = new ProductIterator(this);
        while (it.hasNext()) {
            visitor.visitProduct(it.Next());
        }
    };
}
