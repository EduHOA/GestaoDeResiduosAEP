package com.ecoapp.waste_management.dto.reward;

import com.ecoapp.waste_management.enums.RewardType;

import java.time.LocalDateTime;

public class RewardDTO {
    private Long id;
    private String name;
    private String description;
    private Integer pointsRequired;
    private RewardType type;
    private Integer availableQuantity;
    private Integer redeemedQuantity;
    private String partnerName;
    private String imageUrl;
    private String termsConditions;
    private LocalDateTime expiryDate;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public RewardDTO() {}

    public RewardDTO(Long id, String name, String description, Integer pointsRequired, RewardType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pointsRequired = pointsRequired;
        this.type = type;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPointsRequired() { return pointsRequired; }
    public void setPointsRequired(Integer pointsRequired) { this.pointsRequired = pointsRequired; }

    public RewardType getType() { return type; }
    public void setType(RewardType type) { this.type = type; }

    public Integer getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; }

    public Integer getRedeemedQuantity() { return redeemedQuantity; }
    public void setRedeemedQuantity(Integer redeemedQuantity) { this.redeemedQuantity = redeemedQuantity; }

    public String getPartnerName() { return partnerName; }
    public void setPartnerName(String partnerName) { this.partnerName = partnerName; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTermsConditions() { return termsConditions; }
    public void setTermsConditions(String termsConditions) { this.termsConditions = termsConditions; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}