package util;

import models.Offering;
import models.OfferingList;


/**
 * A subclass of ListIterator to iterate over an Offeringlist.
 */
public class OfferingIterator implements ListIterator<OfferingList, Offering> {
    private final OfferingList iterable;
    private int idx = 0;

    public OfferingIterator(OfferingList list) {
        iterable = list;
    }

    /**
     * Check if there is another offering.
     * @return true if there is an offering, false otherwise.
     */
    public boolean hasNext() {
        return this.idx < this.iterable.size();
    }

    /**
     * if hasNext, return the next offering, null otherwise
     * @return the next offering if hasNext, null otherwise.
     */
    public Offering Next() {
        return this.iterable.get(this.idx++);
    }

    /**
     * Move the current offering before the first one.
     */
    public void MoveToHead() {
        var item = this.iterable.remove(this.idx);
        this.iterable.add(0, item);
    }

    /**
     * Remove the current offering.
     */
    public void Remove() {
        this.iterable.remove(this.idx);
    }
}
