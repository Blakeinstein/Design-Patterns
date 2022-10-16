package models;

/**
 * Class for an offering created by a person for a product.
 */
public class Offering {
    /**
     * Person who created the offering
     */
    private final Person person;
    /**
     * Product the offering was created for
     */
    private final Product product;
    /**
     * Whether the offering is still a prototype
     */
    private boolean submitted = false;

    public Offering(Product product, Person person) {
        this.product = product;
        this.person = person;
    }

    /**
     * Submit the offering
     * @return true if the product was not submitted before
     */
    public boolean submit() {
        var r = !this.submitted;
        this.submitted = true;
        return r;
    }

    /**
     * Get the product involved in the offering
     * @return product in the offering
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Get the person involved in the offering
     * @return person in the offering
     */
    public Person getPerson() {
        return this.person;
    }
}
