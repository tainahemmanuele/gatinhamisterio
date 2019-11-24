package com.gm.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gm.order.Order;


import java.util.Set;

public class OrderConverter extends OrderListConverter{
    @Override
    protected TypeReference<Set<Order>> createTypeReference() {
        return new TypeReference<Set<Order>>(){};
    }
}
