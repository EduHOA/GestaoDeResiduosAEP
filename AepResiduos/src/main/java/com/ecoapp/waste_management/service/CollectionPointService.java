package com.ecoapp.waste_management.service;

import com.ecoapp.waste_management.entity.CollectionPoint;
import com.ecoapp.waste_management.enums.CollectionPointStatus;
import com.ecoapp.waste_management.repository.CollectionPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CollectionPointService {

    @Autowired
    private CollectionPointRepository collectionPointRepository;

    public List<CollectionPoint> getActiveCollectionPoints() {
        return collectionPointRepository.findByStatus(CollectionPointStatus.ACTIVE);
    }

    public List<CollectionPoint> getNearbyCollectionPoints(double latitude, double longitude, double radiusKm) {
        return collectionPointRepository.findNearbyCollectionPoints(latitude, longitude, radiusKm);
    }

    public List<CollectionPoint> getAvailableCollectionPoints() {
        return collectionPointRepository.findAvailableCollectionPoints();
    }

    public CollectionPoint updateCapacity(Long collectionPointId, int newLoad) {
        CollectionPoint collectionPoint = collectionPointRepository.findById(collectionPointId)
                .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado"));

        collectionPoint.setCurrentLoad(newLoad);

        // Atualizar status baseado na capacidade
        double utilizationPercentage = (double) newLoad / collectionPoint.getCapacity();
        if (utilizationPercentage >= 0.9) {
            collectionPoint.setStatus(CollectionPointStatus.FULL);
        } else {
            collectionPoint.setStatus(CollectionPointStatus.ACTIVE);
        }

        return collectionPointRepository.save(collectionPoint);
    }
}