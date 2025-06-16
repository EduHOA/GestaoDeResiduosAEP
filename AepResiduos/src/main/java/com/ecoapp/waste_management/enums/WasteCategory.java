package com.ecoapp.waste_management.enums;

public enum WasteCategory {
    RECYCLABLE("Reciclável"),
    ORGANIC("Orgânico"),
    HAZARDOUS("Perigoso"),
    SPECIAL("Especial"),
    ELECTRONIC("Eletrônico");

    private final String description;

    WasteCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}