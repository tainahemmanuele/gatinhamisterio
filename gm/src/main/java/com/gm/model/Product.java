package com.gm.model;

import com.gm.util.ProductType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Product extends Item implements Serializable {


    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "distributor", nullable = false)
    private String distributor;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ProductType type;

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

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "brand='" + brand + '\'' +
                ", distributor='" + distributor + '\'' +
                ", type=" + type +
                '}';
    }

    public Product(String barcode, String name, float cost, float price, int stock, String brand, String distributor, ProductType type) {
        super( barcode, name, cost, price, stock);
        this.brand = brand;
        this.distributor = distributor;
        this.type = type;
    }


}
