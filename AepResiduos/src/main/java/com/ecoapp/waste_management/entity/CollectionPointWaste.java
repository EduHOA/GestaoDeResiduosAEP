package com.ecoapp.waste_management.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "collection_point_wastes")
public class CollectionPointWaste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_point_id", nullable = false)
    private CollectionPoint collectionPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_id", nullable = false)
    private Waste waste;

    @Column(name = "max_capacity_kg", precision = 8, scale = 2)
    private BigDecimal maxCapacityKg;

    @Column(name = "current_load_kg", precision = 8, scale = 2, columnDefinition = "decimal(8,2) default 0.00")
    private BigDecimal currentLoadKg = BigDecimal.valueOf(0.0);

    // Construtores
    public CollectionPointWaste() {}

    public CollectionPointWaste(CollectionPoint collectionPoint, Waste waste) {
        this.collectionPoint = collectionPoint;
        this.waste = waste;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CollectionPoint getCollectionPoint() { return collectionPoint; }
    public void setCollectionPoint(CollectionPoint collectionPoint) { this.collectionPoint = collectionPoint; }

    public Waste getWaste() { return waste; }
    public void setWaste(Waste waste) { this.waste = waste; }

    public BigDecimal getMaxCapacityKg() { return maxCapacityKg; }
    public void setMaxCapacityKg(BigDecimal maxCapacityKg) { this.maxCapacityKg = maxCapacityKg; }

    public BigDecimal getCurrentLoadKg() { return currentLoadKg; }
    public void setCurrentLoadKg(BigDecimal currentLoadKg) { this.currentLoadKg = currentLoadKg; }
}