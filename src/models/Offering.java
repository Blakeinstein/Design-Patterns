package models;

public class Offering {
    private final Person person;
    private final Product product;
    private boolean submitted = false;

    public Offering(Product product, Person person) {
        this.product = product;
        this.person = person;
    }

    public boolean submit() {
        var r = !this.submitted;
        this.submitted = true;
        return r;
    }

    public Product getProduct() {
        return this.product;
    }

    public Person getPerson() {
        return this.person;
    }
}
