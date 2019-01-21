package com.example.billsplitapp;

public class PurchaseItem {
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
