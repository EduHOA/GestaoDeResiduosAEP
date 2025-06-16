package com.ecoapp.waste_management.enums;

public enum RewardStatus {
    ACTIVE("Ativo"),
    USED("Usado"),
    EXPIRED("Expirado"),
    CANCELLED("Cancelado");

    private final String description;

    RewardStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}