package util;

import models.ClassProductList;
import models.Product;

/**
 * A subclass of ListIterator to iterate over a ClassProductList.
 */
public class ProductIterator implements ListIterator<ClassProductList, Product> {
    private ClassProductList iterable;
    private int idx = 0;

    public ProductIterator(ClassProductList list) {
        iterable = list;
    }

    /**
     * Check if there is another product.
     * @return true if there is another product, false otherwise.
     */
    @Override
    public boolean hasNext() {
        return this.idx < this.iterable.size();
    }

    /**
     * if hasNext, return the next product, null otherwise.
     * @return returns next product, null if hasNext() is false.
     */
    @Override
    public Product Next() {
        return this.iterable.get(this.idx++);
    }

    /**
     * Set the current product to the location before the first product.
     */
    @Override
    public void MoveToHead() {

    }

    /**
     * Remove the current product from the list.
     */
    @Override
    public void Remove() {

    }
}
