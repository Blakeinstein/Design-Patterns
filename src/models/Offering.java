package models;

public class Offering {
    private Product product;
    private boolean submitted = false;

    public Offering(Product product) {
        this.product = product;
    }

    public boolean submit() {
        var r = this.submitted == false;
        this.submitted = true;
        return r;
    }
}
