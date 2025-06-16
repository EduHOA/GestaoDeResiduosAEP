package com.ecoapp.waste_management.repository;

import com.ecoapp.waste_management.entity.Waste;
import com.ecoapp.waste_management.enums.WasteCategory;
import com.ecoapp.waste_management.enums.WasteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WasteRepository extends JpaRepository<Waste, Long> {
    List<Waste> findByType(WasteType type);
    List<Waste> findByCategory(WasteCategory category);
    List<Waste> findByOrderByPointsPerKgDesc();
    List<Waste> findByNameContainingIgnoreCase(String query);
}