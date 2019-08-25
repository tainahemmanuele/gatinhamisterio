package com.gm.util;

public enum SubscriptionPaymentType {
    BOLETO("Boleto Bancário"),
    CREDIT("Cartão de Crédito"),
    DEBIT("Cartão de Débito");

    private String paymentType;

    SubscriptionPaymentType(String type) {
        this.paymentType = type;
    }

    public String getPaymentType() {
        return this.paymentType;
    }
}
