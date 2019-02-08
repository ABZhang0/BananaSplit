package com.example.bananasplit;

import java.io.Serializable;

public class Buyer implements Serializable {
    private int portrait;
    private String name;
    private Double bill;
    private boolean selected;

    public Buyer(String name) {
        portrait = R.drawable.ic_android_black;
        this.name = name;
        bill = 0.00;
        selected = false;
    }

    public void addToBill(Double amount) {
        bill += amount;
    }

    public void subFromBill(Double amount) {
        bill -= amount;
    }

    public Double getBill() {
        return bill;
    }

    public String getName() {
        return name;
    }

    public int getPortrait() {
        return portrait;
    }

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }
}
