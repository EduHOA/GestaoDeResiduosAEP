package com.ecoapp.waste_management.entity;

import com.ecoapp.waste_management.enums.RewardType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "points_required", nullable = false)
    private Integer pointsRequired;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RewardType type;

    @Column(name = "available_quantity", columnDefinition = "int default 0")
    private Integer availableQuantity = 0;

    @Column(name = "redeemed_quantity", columnDefinition = "int default 0")
    private Integer redeemedQuantity = 0;

    @Column(name = "partner_name", length = 100)
    private String partnerName;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "terms_conditions", length = 1000)
    private String termsConditions;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "reward", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserReward> userRewards;

    // Construtores
    public Reward() {}

    public Reward(String name, String description, Integer pointsRequired, RewardType type) {
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

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<UserReward> getUserRewards() { return userRewards; }
    public void setUserRewards(List<UserReward> userRewards) { this.userRewards = userRewards; }
}