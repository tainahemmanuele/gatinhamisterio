package com.gm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "box")
public class Box extends Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "box_id", nullable = false)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "box_id")
    private Set<Product> products;


    public Set<Product> getProducts() {
        return products;
    }

    public Box() {
    }

    public Box(String barcode, String name, float cost, float price, int stock, Set<Product> products) {
        super(barcode, name, cost, price, stock);
        this.products = products;
    }

    @Override
    public String toString() {
        return "Box{" +
                "products=" + products +
                '}';
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
