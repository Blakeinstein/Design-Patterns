package util;

import models.Offering;
import models.OfferingList;


/**
 * A subclass of ListIterator to iterate over an Offeringlist.
 */
public class OfferingIterator implements ListIterator<OfferingList, Offering> {
    private OfferingList iterable;
    private int idx = 0;

    public OfferingIterator(OfferingList list) {
        iterable = list;
    }

    /**
     * Check if there is another offering.
     * @return true if there is an offering, false otherwise.
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * if hasNext, return the next offering, null otherwise
     * @return the next offering if hasNext, null otherwise.
     */
    @Override
    public Offering Next() {
        return null;
    }

    /**
     * Move the current offering before the first one.
     */
    @Override
    public void MoveToHead() {

    }

    /**
     * Remove the current offering.
     */
    @Override
    public void Remove() {

    }
}
