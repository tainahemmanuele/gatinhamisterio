package com.gm.util;

public enum SubscriptionType {
    DEFAULT("Fada Sensata"),
    PREMIUM("Cristal Lapidado");

    private String subscriptionType;

    SubscriptionType(String type) {
        this.subscriptionType = type;
    }

    public String getSubscriptionType() {
        return this.subscriptionType;
    }
}