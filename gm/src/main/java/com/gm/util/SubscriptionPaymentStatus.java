package com.gm.util;

public enum SubscriptionPaymentStatus {
    REQUESTED("Requisitado"),
    PROCESSING("Processando"),
    PAID("Pago");

    private String paymentStatus;

    SubscriptionPaymentStatus(String status) {
        this.paymentStatus = status;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }
}
