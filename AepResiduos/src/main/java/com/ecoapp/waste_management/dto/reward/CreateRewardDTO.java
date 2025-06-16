package com.ecoapp.waste_management.dto.reward;

import com.ecoapp.waste_management.enums.RewardType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class CreateRewardDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String name;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String description;

    @NotNull(message = "Pontos necessários são obrigatórios")
    @Positive(message = "Pontos necessários devem ser positivos")
    private Integer pointsRequired;

    @NotNull(message = "Tipo é obrigatório")
    private RewardType type;

    @PositiveOrZero(message = "Quantidade disponível deve ser zero ou positiva")
    private Integer availableQuantity;

    @Size(max = 100, message = "Nome do parceiro deve ter no máximo 100 caracteres")
    private String partnerName;

    @Size(max = 255, message = "URL da imagem deve ter no máximo 255 caracteres")
    private String imageUrl;

    @Size(max = 1000, message = "Termos e condições devem ter no máximo 1000 caracteres")
    private String termsConditions;

    @Future(message = "Data de expiração deve ser no futuro")
    private LocalDateTime expiryDate;

    // Getters e Setters
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

    public String getPartnerName() { return partnerName; }
    public void setPartnerName(String partnerName) { this.partnerName = partnerName; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTermsConditions() { return termsConditions; }
    public void setTermsConditions(String termsConditions) { this.termsConditions = termsConditions; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
}