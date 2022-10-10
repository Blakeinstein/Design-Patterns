package models;

import controller.NodeVisitor;

import java.util.ArrayList;

/**
 * A list class of products.
 */
public class ClassProductList extends ArrayList<Product> {
    public void accept(NodeVisitor visitor) {};
}
