package com.ecoapp.waste_management.enums;

public enum AppointmentStatus {
    PENDING("Pendente"),
    CONFIRMED("Confirmado"),
    IN_PROGRESS("Em Andamento"),
    COMPLETED("Concluído"),
    CANCELLED("Cancelado"),
    NO_SHOW("Não Compareceu");

    private final String description;

    AppointmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}