package com.ecoapp.waste_management.repository;

import com.ecoapp.waste_management.entity.CollectionPoint;
import com.ecoapp.waste_management.enums.CollectionPointStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Long> {
    List<CollectionPoint> findByStatus(CollectionPointStatus status);

    @Query("SELECT cp FROM CollectionPoint cp WHERE cp.status = 'ACTIVE' AND " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(cp.latitude)) * " +
            "cos(radians(cp.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(cp.latitude)))) <= :radius")
    List<CollectionPoint> findNearbyCollectionPoints(double latitude, double longitude, double radius);

    @Query("SELECT cp FROM CollectionPoint cp WHERE cp.currentLoad < cp.capacity * 0.9")
    List<CollectionPoint> findAvailableCollectionPoints();
}