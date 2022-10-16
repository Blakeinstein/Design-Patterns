package models;

import java.util.ArrayList;

/**
 * The client of the visitor pattern. Uses the visitor to
 * visit all the products and trading of a given user.
 */
public class Reminder {
    private ArrayList<Trading> overdue;
    private ArrayList<Trading> pending;

    public Reminder() {
        this.overdue = new ArrayList<>();
        this.pending = new ArrayList<>();
    }

    public void addOverdue(Trading trading) {
        this.overdue.add(trading);
    }

    public void addPending(Trading trading) {
        this.pending.add(trading);
    }

    public ArrayList<Trading> getOverdue() {
        return overdue;
    }

    public ArrayList<Trading> getPending() {
        return pending;
    }
}
