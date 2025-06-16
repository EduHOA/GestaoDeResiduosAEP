package com.ecoapp.waste_management.service;

import com.ecoapp.waste_management.entity.Reward;
import com.ecoapp.waste_management.entity.User;
import com.ecoapp.waste_management.entity.UserReward;
import com.ecoapp.waste_management.enums.RewardStatus;
import com.ecoapp.waste_management.repository.RewardRepository;
import com.ecoapp.waste_management.repository.UserRewardRepository;
import com.ecoapp.waste_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private UserRewardRepository userRewardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<Reward> getAvailableRewards() {
        return rewardRepository.findAvailableRewards(LocalDateTime.now());
    }

    public List<Reward> getAffordableRewards(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return rewardRepository.findAffordableRewards(user.getPoints());
    }

    public UserReward redeemReward(Long userId, Long rewardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Recompensa não encontrada"));

        // Verificar se o usuário tem pontos suficientes
        if (user.getPoints() < reward.getPointsRequired()) {
            throw new RuntimeException("Pontos insuficientes");
        }

        // Verificar disponibilidade
        if (reward.getAvailableQuantity() <= reward.getRedeemedQuantity()) {
            throw new RuntimeException("Recompensa esgotada");
        }

        // Deduzir pontos do usuário
        if (!userService.deductPoints(userId, reward.getPointsRequired())) {
            throw new RuntimeException("Erro ao deduzir pontos");
        }

        // Criar registro de resgate
        UserReward userReward = new UserReward(user, reward, reward.getPointsRequired());
        userReward.setRedemptionCode(generateRedemptionCode());

        if (reward.getExpiryDate() != null) {
            userReward.setExpiresAt(reward.getExpiryDate());
        }

        // Atualizar quantidade resgatada
        reward.setRedeemedQuantity(reward.getRedeemedQuantity() + 1);
        rewardRepository.save(reward);

        return userRewardRepository.save(userReward);
    }

    private String generateRedemptionCode() {
        return "ECO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<UserReward> getUserRewards(Long userId) {
        return userRewardRepository.findByUserId(userId);
    }

    public UserReward useReward(String redemptionCode) {
        List<UserReward> userRewards = userRewardRepository.findByRedemptionCode(redemptionCode);

        if (userRewards.isEmpty()) {
            throw new RuntimeException("Código de resgate inválido");
        }

        UserReward userReward = userRewards.get(0);

        if (userReward.getStatus() != RewardStatus.ACTIVE) {
            throw new RuntimeException("Recompensa já utilizada ou expirada");
        }

        if (userReward.getExpiresAt() != null && userReward.getExpiresAt().isBefore(LocalDateTime.now())) {
            userReward.setStatus(RewardStatus.EXPIRED);
            throw new RuntimeException("Recompensa expirada");
        }

        userReward.setStatus(RewardStatus.USED);
        userReward.setUsedAt(LocalDateTime.now());

        return userRewardRepository.save(userReward);
    }
}