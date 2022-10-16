package models;

import controller.NodeVisitor;

import java.util.Date;

public class Trading {

    private final Product product;
    private final Person person;
    private final Date due;


    public Trading(Product product, Person person, Date due) {
        this.person = person;
        this.product = product;
        this.due = due;
    }
    public boolean accept(NodeVisitor visitor) {
        return this.due.getTime() > (new Date()).getTime();
    }

    public Product getProduct() {
        return product;
    }

    public Person getPerson() {
        return person;
    }

    public Date getDueDate() {
        return due;
    }
}
