package util;

import java.util.ArrayList;

/**
 * Abstract iterator class to defined iterating functions.
 * @param <T> ArrayList container for Y
 * @param <Y> Iterable
 * Implements the iterator design pattern.
 */
public interface ListIterator<T extends ArrayList<Y>, Y> {

    /**
     * Check if there is an item up ahead.
     * @return true if there is an item up ahead, false otherwise.
     */
    boolean hasNext();

    /**
     * Returns the item up ahead.
     * @return returns next item, null if hasNext() is false.
     */
    Y Next();

    /**
     * Set the current item to the location before the first item.
     */
    void MoveToHead();

    /**
     * Remove the current item from the list.
     */
    void Remove();
}
