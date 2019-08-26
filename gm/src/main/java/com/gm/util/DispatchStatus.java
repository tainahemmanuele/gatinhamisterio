package com.gm.util;

public enum DispatchStatus {
    WAITING("Aguardando Pagamento"),
    SHIPPING("Preparando o Envio"),
    SENT("Enviado");

    private String dispatchStatus;

    DispatchStatus(String status) {
        this.dispatchStatus = status;
    }

    public String getDispatchStatus() {
        return this.dispatchStatus;
    }
}
