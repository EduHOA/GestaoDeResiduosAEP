package com.ecoapp.waste_management.dto.appointment;

import com.ecoapp.waste_management.enums.WasteType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CreateAppointmentDTO {
    @NotNull(message = "Data e hora são obrigatórias")
    @Future(message = "Data deve ser no futuro")
    private LocalDateTime scheduledDateTime;

    private Long collectionPointId;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String collectionAddress;

    @NotNull(message = "Tipo de resíduo é obrigatório")
    private WasteType wasteType;

    @Positive(message = "Quantidade estimada deve ser positiva")
    private Double estimatedQuantity;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String notes;

    // Getters e Setters
    public LocalDateTime getScheduledDateTime() { return scheduledDateTime; }
    public void setScheduledDateTime(LocalDateTime scheduledDateTime) { this.scheduledDateTime = scheduledDateTime; }

    public Long getCollectionPointId() { return collectionPointId; }
    public void setCollectionPointId(Long collectionPointId) { this.collectionPointId = collectionPointId; }

    public String getCollectionAddress() { return collectionAddress; }
    public void setCollectionAddress(String collectionAddress) { this.collectionAddress = collectionAddress; }

    public WasteType getWasteType() { return wasteType; }
    public void setWasteType(WasteType wasteType) { this.wasteType = wasteType; }

    public Double getEstimatedQuantity() { return estimatedQuantity; }
    public void setEstimatedQuantity(Double estimatedQuantity) { this.estimatedQuantity = estimatedQuantity; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}