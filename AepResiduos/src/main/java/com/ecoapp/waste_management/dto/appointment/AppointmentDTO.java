package com.ecoapp.waste_management.dto.appointment;

import com.ecoapp.waste_management.enums.AppointmentStatus;
import com.ecoapp.waste_management.enums.WasteType;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Long id;
    private String userName;
    private String collectionPointName;
    private LocalDateTime scheduledDateTime;
    private String collectionAddress;
    private WasteType wasteType;
    private Double estimatedQuantity;
    private Double actualQuantity;
    private Integer pointsEarned;
    private AppointmentStatus status;
    private String notes;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getCollectionPointName() { return collectionPointName; }
    public void setCollectionPointName(String collectionPointName) { this.collectionPointName = collectionPointName; }

    public LocalDateTime getScheduledDateTime() { return scheduledDateTime; }
    public void setScheduledDateTime(LocalDateTime scheduledDateTime) { this.scheduledDateTime = scheduledDateTime; }

    public String getCollectionAddress() { return collectionAddress; }
    public void setCollectionAddress(String collectionAddress) { this.collectionAddress = collectionAddress; }

    public WasteType getWasteType() { return wasteType; }
    public void setWasteType(WasteType wasteType) { this.wasteType = wasteType; }

    public Double getEstimatedQuantity() { return estimatedQuantity; }
    public void setEstimatedQuantity(Double estimatedQuantity) { this.estimatedQuantity = estimatedQuantity; }

    public Double getActualQuantity() { return actualQuantity; }
    public void setActualQuantity(Double actualQuantity) { this.actualQuantity = actualQuantity; }

    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}