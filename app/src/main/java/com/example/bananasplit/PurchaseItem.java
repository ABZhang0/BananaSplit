package com.example.bananasplit;

import java.io.Serializable;

public class PurchaseItem implements Serializable {
    private String name;
    private Double price;

    public PurchaseItem(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double price() {
        return price;
    }
}
