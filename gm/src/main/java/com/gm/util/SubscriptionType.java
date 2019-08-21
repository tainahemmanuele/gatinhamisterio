package com.gm.util;

public enum SubscriptionType {
    DEFAULT("Fada Alguma Coisa"),
    PREMIUM("Cristal Alguma Coisa");

    private String subscriptionType;

    SubscriptionType(String type) {
        this.subscriptionType = type;
    }

    public String getSubscriptionType() {
        return this.subscriptionType;
    }
}