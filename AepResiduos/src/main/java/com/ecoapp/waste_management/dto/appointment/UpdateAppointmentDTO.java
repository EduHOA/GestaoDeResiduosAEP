package com.ecoapp.waste_management.dto.appointment;

import com.ecoapp.waste_management.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateAppointmentDTO {
    @Future(message = "Data deve ser no futuro")
    private LocalDateTime scheduledDateTime;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String collectionAddress;

    @Positive(message = "Quantidade estimada deve ser positiva")
    private Double estimatedQuantity;

    @Positive(message = "Quantidade real deve ser positiva")
    private Double actualQuantity;

    private AppointmentStatus status;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String notes;

    // Getters e Setters
    public LocalDateTime getScheduledDateTime() { return scheduledDateTime; }
    public void setScheduledDateTime(LocalDateTime scheduledDateTime) { this.scheduledDateTime = scheduledDateTime; }

    public String getCollectionAddress() { return collectionAddress; }
    public void setCollectionAddress(String collectionAddress) { this.collectionAddress = collectionAddress; }

    public Double getEstimatedQuantity() { return estimatedQuantity; }
    public void setEstimatedQuantity(Double estimatedQuantity) { this.estimatedQuantity = estimatedQuantity; }

    public Double getActualQuantity() { return actualQuantity; }
    public void setActualQuantity(Double actualQuantity) { this.actualQuantity = actualQuantity; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}