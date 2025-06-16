package com.ecoapp.waste_management.dto.user;

import com.ecoapp.waste_management.enums.RewardStatus;

import java.time.LocalDateTime;

public class UserRewardDTO {
    private Long id;
    private String rewardName;
    private String rewardDescription;
    private Integer pointsUsed;
    private RewardStatus status;
    private String redemptionCode;
    private LocalDateTime usedAt;
    private LocalDateTime expiresAt;
    private LocalDateTime redeemedAt;

    public UserRewardDTO() {}

    public UserRewardDTO(Long id, String rewardName, Integer pointsUsed, RewardStatus status) {
        this.id = id;
        this.rewardName = rewardName;
        this.pointsUsed = pointsUsed;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRewardName() { return rewardName; }
    public void setRewardName(String rewardName) { this.rewardName = rewardName; }

    public String getRewardDescription() { return rewardDescription; }
    public void setRewardDescription(String rewardDescription) { this.rewardDescription = rewardDescription; }

    public Integer getPointsUsed() { return pointsUsed; }
    public void setPointsUsed(Integer pointsUsed) { this.pointsUsed = pointsUsed; }

    public RewardStatus getStatus() { return status; }
    public void setStatus(RewardStatus status) { this.status = status; }

    public String getRedemptionCode() { return redemptionCode; }
    public void setRedemptionCode(String redemptionCode) { this.redemptionCode = redemptionCode; }

    public LocalDateTime getUsedAt() { return usedAt; }
    public void setUsedAt(LocalDateTime usedAt) { this.usedAt = usedAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public LocalDateTime getRedeemedAt() { return redeemedAt; }
    public void setRedeemedAt(LocalDateTime redeemedAt) { this.redeemedAt = redeemedAt; }
}