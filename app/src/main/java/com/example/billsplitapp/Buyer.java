package com.example.billsplitapp;

import java.io.Serializable;

public class Buyer implements Serializable {
    private int portrait;
    private String name;
    private Double bill;

    public Buyer(String name) {
        portrait = R.drawable.ic_android_black;
        this.name = name;
        bill = 0.00;
    }

    public void addToBill(Double amount) {
        bill += amount;
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
}
