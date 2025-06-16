package com.ecoapp.waste_management.repository;

import com.ecoapp.waste_management.entity.UserReward;
import com.ecoapp.waste_management.enums.RewardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRewardRepository extends JpaRepository<UserReward, Long> {
    List<UserReward> findByUserId(Long userId);
    List<UserReward> findByUserIdAndStatus(Long userId, RewardStatus status);
    List<UserReward> findByRedemptionCode(String redemptionCode);
}