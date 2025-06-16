package com.ecoapp.waste_management.entity;

import com.ecoapp.waste_management.enums.WasteCategory;
import com.ecoapp.waste_management.enums.WasteType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "wastes")
public class Waste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WasteType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private WasteCategory category;

    @Column(name = "points_per_kg", nullable = false)
    private Integer pointsPerKg;

    @Column(name = "instructions", length = 500)
    private String instructions;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "waste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CollectionPointWaste> collectionPoints;

    // Construtores
    public Waste() {}

    public Waste(String name, WasteType type, WasteCategory category, Integer pointsPerKg) {
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

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<CollectionPointWaste> getCollectionPoints() { return collectionPoints; }
    public void setCollectionPoints(List<CollectionPointWaste> collectionPoints) { this.collectionPoints = collectionPoints; }
}
