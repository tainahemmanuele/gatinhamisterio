package com.gm.converters;

import com.gm.product.Product;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Set;

public class ProductConverter extends ProductListConverter{
    @Override
    protected TypeReference<Set<Product>> createTypeReference() {
        return new TypeReference<Set<Product>>(){};
    }
}
