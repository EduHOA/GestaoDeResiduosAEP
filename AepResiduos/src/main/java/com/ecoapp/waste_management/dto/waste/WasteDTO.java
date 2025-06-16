package com.ecoapp.waste_management.dto.waste;

import com.ecoapp.waste_management.enums.WasteCategory;
import com.ecoapp.waste_management.enums.WasteType;

import java.time.LocalDateTime;

public class WasteDTO {
    private Long id;
    private String name;
    private WasteType type;
    private WasteCategory category;
    private Integer pointsPerKg;
    private String instructions;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;

    public WasteDTO() {}

    public WasteDTO(Long id, String name, WasteType type, WasteCategory category, Integer pointsPerKg) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
        this.pointsPerKg = pointsPerKg;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public WasteType getType() { return type; }
    public void setType(WasteType type) { this.type = type; }

    public WasteCategory getCategory() { return category; }
    public void setCategory(WasteCategory category) { this.category = category; }

    public Integer getPointsPerKg() { return pointsPerKg; }
    public void setPointsPerKg(Integer pointsPerKg) { this.pointsPerKg = pointsPerKg; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}