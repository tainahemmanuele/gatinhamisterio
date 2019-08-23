package com.gm.util;

public enum ProductType {
    BATOM("Batom"),
    SOMBRA("Sombra"),
    RIMEL("Rimel"),
    BASE("Base"),
    PO_FACIAL("Po Facial"),
    PROTETOR_SOLAR("Protetor Solar"),
    DEMAQUILANTE("Demaquilante"),
    AGUA_MICELAR("Agua Micelar"),
    TONICO_FACIAL("Tonico Facial"),
    HIDRATANTE("Hidratante"),
    MASCARA_FACIAL("Mascara"),
    ESFOLIANTE("Esfoliante");


    private String productType;

    ProductType(String type) {
        this.productType = type;
    }

    public String getSubscriptionType() {
        return this.productType;
    }

}
