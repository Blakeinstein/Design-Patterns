package models;

import controller.NodeVisitor;

import java.util.ArrayList;

public class Product {

    public enum PRODUCT_TYPE {
        Meat, Produce
    }

    private final Product.PRODUCT_TYPE type;
    private final String Name;

    private final ArrayList<Trading> tradings;

    public Product(String productName, String productType) {
        this(productName, productType.equals("meat") ? PRODUCT_TYPE.Meat : PRODUCT_TYPE.Produce);
    }

    public Product(String productName, PRODUCT_TYPE productType) {
       Name = productName;
       type = productType;
       this.tradings = new ArrayList<>();
    }

    public String getName() {
        return Name;
    }

    public PRODUCT_TYPE getType() {
        return type;
    }

    public void addTrading(Trading trading) {
        this.tradings.add(trading);
    }


    public ArrayList<Trading> getTradings() {
        return this.tradings;
    }

    public void accept(NodeVisitor visitor) {
        for (var t : this.tradings) {
            visitor.visitTrading(t);
        }
    }
}
