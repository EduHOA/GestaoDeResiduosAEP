package com.ecoapp.waste_management.repository;

import com.ecoapp.waste_management.entity.Reward;
import com.ecoapp.waste_management.enums.RewardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findByIsActiveTrue();
    List<Reward> findByType(RewardType type);

    @Query("SELECT r FROM Reward r WHERE r.isActive = true AND " +
            "(r.expiryDate IS NULL OR r.expiryDate > :now) AND " +
            "r.availableQuantity > r.redeemedQuantity")
    List<Reward> findAvailableRewards(LocalDateTime now);

    @Query("SELECT r FROM Reward r WHERE r.pointsRequired <= :userPoints AND r.isActive = true")
    List<Reward> findAffordableRewards(int userPoints);
}