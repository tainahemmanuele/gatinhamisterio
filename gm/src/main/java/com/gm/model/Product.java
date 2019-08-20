package com.gm.model;

public class Product extends Item {
    private String brand;
    private String distributor;
    private String type;

    public Product(String barcode, String name, String brand, String distributor, String type,float cost, float price, int stock) {
        super(barcode, name,  cost, price, stock);
        this.brand = brand;
        this.distributor = distributor;
        this.type = type.toLowerCase();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
