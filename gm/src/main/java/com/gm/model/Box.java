package com.gm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Box extends Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ElementCollection
    @CollectionTable(name="listProducts")
    private List<Product> productsBox;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProductsBox() {
        return productsBox;
    }

    public void setProductsBox(List<Product> productsBox) {
        this.productsBox = productsBox;
    }
}
