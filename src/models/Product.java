package models;

public class Product {
    public static enum PRODUCT_TYPE {
        Meat, Produce
    }

    private Product.PRODUCT_TYPE type;
    private String Name;

    public Product(String productName, String productType) throws Exception {
        Name = productName;
        switch (productType.toLowerCase()) {
            case "meat":
                type = PRODUCT_TYPE.Meat;
                break;
            case "produce":
                type = PRODUCT_TYPE.Produce;
                break;
            default:
                throw new Exception("Invalid product type");
        }
    }

    public String getName() {
        return Name;
    }

    public PRODUCT_TYPE getType() {
        return type;
    }
}
