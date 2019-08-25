package com.gm.util;

public enum PaymentType {
    BOLETO("Boleto Bancário"),
    CREDIT("Cartão de Crédito"),
    DEBIT("Cartão de Débito");

    private String paymentType;

    PaymentType(String type) {
        this.paymentType = type;
    }

    public String getPaymentType() {
        return this.paymentType;
    }
}
