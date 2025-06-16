package com.ecoapp.waste_management.dto.reward;

import jakarta.validation.constraints.NotNull;

public class RewardRedemptionDTO {
    @NotNull(message = "ID da recompensa é obrigatório")
    private Long rewardId;

    public RewardRedemptionDTO() {}

    public RewardRedemptionDTO(Long rewardId) {
        this.rewardId = rewardId;
    }

    public Long getRewardId() { return rewardId; }
    public void setRewardId(Long rewardId) { this.rewardId = rewardId; }
}