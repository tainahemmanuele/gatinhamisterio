package com.gm.item;

import javax.persistence.*;

@MappedSuperclass
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "barcode", nullable = false)
    private String barcode;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "cost", nullable = false)
    private float cost;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "stock", nullable = false)
    private int stock;

    public Item() {
    }

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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }



}
