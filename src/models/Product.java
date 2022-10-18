package models;

import controller.NodeVisitor;
import util.Files;
import util.OfferingIterator;

import java.util.ArrayList;

/**
 * Class for a product
 */
public class Product {

    /**
     * Type enum of the product
     * Meat = 0
     * Produce = 1
     */
    public enum PRODUCT_TYPE {
        Meat, Produce
    }

    /**
     * Type of the current product.
     */
    private final Product.PRODUCT_TYPE type;
    /**
     * Name of the current product.
     */
    private final String Name;
    /**
     * List of tradings for the current product.
     */
    private final ArrayList<Trading> tradings;

    /**
     * List of offerings for the current product.
     */
    private final OfferingList offeringList;

    /**
     * Overloaded constructor with string product type
     * @param productName name of the product
     * @param productType type of the product ("meat"/"produce")
     */
    public Product(String productName, String productType) {
        this(productName, productType.equalsIgnoreCase("meat") ? PRODUCT_TYPE.Meat : PRODUCT_TYPE.Produce);
    }

    public Product(String productName, PRODUCT_TYPE productType) {
       Name = productName;
       type = productType;
       this.tradings = new ArrayList<>();
       this.offeringList = new OfferingList();
    }

    /**
     * Get the name of the current product
     * @return name of the product
     */
    public String getName() {
        return Name;
    }

    /**
     * Get the type of the current product
     * @return type of the product
     */
    public PRODUCT_TYPE getType() {
        return type;
    }

    /**
     * Add a trading for the current product
     * @param trading the trading to add
     */
    public void addTrading(Trading trading) {
        this.tradings.add(trading);
    }

    /**
     * Get a list of tradings for the current product
     * @return list of tradings
     */
    public ArrayList<Trading> getTradings() {
        return this.tradings;
    }


    public Offering addOffering(Person p) throws Exception {
        var it = new OfferingIterator(this.offeringList);
        while (it.hasNext()) {
            if (it.Next().getPerson().getName().equals(p.getName())) {
                throw new Exception("You already have an offering for this product");
            }
        }
        var o = new Offering(this, p);
        this.offeringList.add(o);
        return o;
    }

    /**
     * Get a list of offerings for the current product
     * @return list of offerings
     */
    public OfferingList getOfferingList() {
        return this.offeringList;
    }

    /**
     * accepts a node visitor and passes it on to the individual tradings
     * of the product
     * @param visitor an instance of a nodevisitor
     */
    public void accept(NodeVisitor visitor) {
        for (var t : this.tradings) {
            visitor.visitTrading(t);
        }
    }
}
