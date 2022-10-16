package models;

import java.util.ArrayList;

/**
 * The client of the visitor pattern. Uses the visitor to
 * visit all the products and trading of a given user.
 */
public class Reminder {
    /**
     * List of tradings that are overdue.
     */
    private final ArrayList<Trading> overdue;
    /**
     * List of tradings with due date in the future.
     */
    private final ArrayList<Trading> pending;

    public Reminder() {
        this.overdue = new ArrayList<>();
        this.pending = new ArrayList<>();
    }

    /**
     * Add an overdue trading
     * @param trading trading to add
     */
    public void addOverdue(Trading trading) {
        this.overdue.add(trading);
    }

    /**
     * Add a pending trading
     * @param trading trading to add
     */
    public void addPending(Trading trading) {
        this.pending.add(trading);
    }

    /**
     * get list of overdue tradings
     * @return list of tradings
     */
    public ArrayList<Trading> getOverdue() {
        return overdue;
    }

    /**
     * get list of pending tradings
     * @return list of tradings
     */
    public ArrayList<Trading> getPending() {
        return pending;
    }
}
