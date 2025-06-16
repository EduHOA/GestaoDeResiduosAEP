package com.ecoapp.waste_management.repository;

import com.ecoapp.waste_management.entity.CollectionPointWaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionPointWasteRepository extends JpaRepository<CollectionPointWaste, Long> {
    List<CollectionPointWaste> findByCollectionPointId(Long collectionPointId);
    List<CollectionPointWaste> findByWasteId(Long wasteId);

    @Query("SELECT cpw FROM CollectionPointWaste cpw WHERE cpw.collectionPoint.id = :collectionPointId AND cpw.currentLoadKg < cpw.maxCapacityKg")
    List<CollectionPointWaste> findAvailableWastesByCollectionPoint(Long collectionPointId);
}