package com.gm.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.gm.product.Product;

import java.util.List;
import java.util.Set;

public abstract class ProductListConverter<T extends Product> extends  DbTypeConverter
        implements DynamoDBTypeConverter<String, Set<T>> {

    public TypeReference<Set<T>> typeReference;

    @Override
    public String convert(Set<T> entities) {
        String outputString = null;
        try {
            outputString = objectMapper.writeValueAsString(entities);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return outputString;
    }

    @Override
    public Set<T> unconvert(String inputString) {
        Set<T> entities = null;
        try {
            if (inputString != null && inputString.length() != 0)
                entities = objectMapper.readValue(inputString, getTypeReference());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    private TypeReference<Set<T>> getTypeReference() {
        return typeReference == null ? (typeReference = createTypeReference()) : typeReference;
    }

    //Due to the creating of TypeReference does not return proper type with generics
    protected abstract TypeReference<Set<T>> createTypeReference();
}