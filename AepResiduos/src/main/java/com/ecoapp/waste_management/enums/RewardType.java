package com.ecoapp.waste_management.enums;

public enum RewardType {
    DISCOUNT("Desconto"),
    PRODUCT("Produto"),
    SERVICE("Serviço"),
    COUPON("Cupom"),
    VOUCHER("Vale"),
    EXPERIENCE("Experiência");

    private final String description;

    RewardType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}