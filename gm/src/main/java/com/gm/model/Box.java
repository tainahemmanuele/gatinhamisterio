package com.gm.model;

public class Box extends Item {
    private Long id;

    public Box(String barcode, String name, float cost, float price, int stock) {
        super(barcode, name,  cost, price, stock);
    }
}
