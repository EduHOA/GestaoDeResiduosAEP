package com.ecoapp.waste_management.enums;

public enum CollectionPointStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    MAINTENANCE("Em Manutenção"),
    FULL("Lotado"),
    SUSPENDED("Suspenso");

    private final String description;

    CollectionPointStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}