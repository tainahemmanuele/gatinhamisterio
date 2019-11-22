package com.gm.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.gm.order.Order;

import java.util.Set;

public class OrderConverter extends DbTypeConverter implements DynamoDBTypeConverter<String, Set<Order>> {

    @Override
    public String convert(Set<Order> entities) {
        String outputString = null;
        try {
            outputString = objectMapper.writeValueAsString(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputString;
    }

    @Override
    public Set<Order> unconvert(String inputString) {
        Set<Order> entities = null;
        try {
            if (inputString != null && inputString.length() != 0)
                entities = objectMapper.readValue(inputString, new TypeReference() {
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
