package com.ecoapp.waste_management.enums;

public enum WasteType {
    PAPER("Papel", 5),
    PLASTIC("Plástico", 10),
    GLASS("Vidro", 15),
    METAL("Metal", 20),
    ELECTRONIC("Eletrônico", 50),
    ORGANIC("Orgânico", 3),
    BATTERY("Bateria", 25),
    OIL("Óleo", 30),
    TEXTILE("Têxtil", 8),
    HAZARDOUS("Perigoso", 40);

    private final String description;
    private final int defaultPointsPerKg;

    WasteType(String description, int defaultPointsPerKg) {
        this.description = description;
        this.defaultPointsPerKg = defaultPointsPerKg;
    }

    public String getDescription() {
        return description;
    }

    public int getDefaultPointsPerKg() {
        return defaultPointsPerKg;
    }
}
