package com.gm.model;

import com.gm.util.ProductType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Product extends Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "distributor", nullable = false)
    private String distributor;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ProductType type;
    @ManyToMany
    private List<Box> boxes;

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

    public Long getId() {
        return id;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
