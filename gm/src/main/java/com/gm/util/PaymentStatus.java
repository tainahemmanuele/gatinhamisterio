package com.gm.util;

public enum PaymentStatus {
    REQUESTED("Requisitado"),
    PROCESSING("Processando"),
    PAID("Pago");

    private String paymentStatus;

    PaymentStatus(String status) {
        this.paymentStatus = status;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }
}
