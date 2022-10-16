package models;

import controller.NodeVisitor;

public class Trading {

    private final Product product;
    private final Person person;

    public Trading(Product product, Person person) {
        this.person = person;
        this.product = product;
    }
    public void accept(NodeVisitor visitor) {};

    public Product getProduct() {
        return product;
    }
}
