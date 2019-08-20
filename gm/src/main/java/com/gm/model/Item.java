package com.gm.model;


public abstract class Item {


    private String barcode;
    private String name;
    private float cost;
    private float price;
    private int stock;



    public Item(String barcode, String name, float cost, float price, int stock) {
        this.barcode = barcode;
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.stock = stock;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
