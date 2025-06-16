package com.ecoapp.waste_management.controller;

import com.ecoapp.waste_management.dto.reward.CreateRewardDTO;
import com.ecoapp.waste_management.dto.reward.RewardDTO;
import com.ecoapp.waste_management.dto.reward.RewardRedemptionDTO;
import com.ecoapp.waste_management.dto.user.UserRewardDTO;
import com.ecoapp.waste_management.entity.Reward;
import com.ecoapp.waste_management.entity.User;
import com.ecoapp.waste_management.entity.UserReward;
import com.ecoapp.waste_management.enums.RewardStatus;
import com.ecoapp.waste_management.repository.RewardRepository;
import com.ecoapp.waste_management.service.RewardService;
import com.ecoapp.waste_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rewards")
@CrossOrigin(origins = "*")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAvailableRewards() {
        try {
            List<Reward> rewards = rewardService.getAvailableRewards();
            List<RewardDTO> rewardDTOs = rewards.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(rewardDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar recompensas: " + e.getMessage());
        }
    }

    @GetMapping("/affordable")
    public ResponseEntity<?> getAffordableRewards() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            List<Reward> rewards = rewardService.getAffordableRewards(user.getId());
            List<RewardDTO> rewardDTOs = rewards.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(rewardDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar recompensas acessíveis: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReward(@PathVariable Long id) {
        try {
            Reward reward = rewardRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recompensa não encontrada"));

            RewardDTO rewardDTO = convertToDTO(reward);
            return ResponseEntity.ok(rewardDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar recompensa: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createReward(@Valid @RequestBody CreateRewardDTO createRewardDTO) {
        try {
            Reward reward = new Reward();
            reward.setName(createRewardDTO.getName());
            reward.setDescription(createRewardDTO.getDescription());
            reward.setPointsRequired(createRewardDTO.getPointsRequired());
            reward.setType(createRewardDTO.getType());
            reward.setAvailableQuantity(createRewardDTO.getAvailableQuantity());
            reward.setRedeemedQuantity(0);
            reward.setPartnerName(createRewardDTO.getPartnerName());
            reward.setImageUrl(createRewardDTO.getImageUrl());
            reward.setTermsConditions(createRewardDTO.getTermsConditions());
            reward.setExpiryDate(createRewardDTO.getExpiryDate());
            reward.setIsActive(true);
            reward.setCreatedAt(LocalDateTime.now());

            Reward savedReward = rewardRepository.save(reward);
            RewardDTO rewardDTO = convertToDTO(savedReward);

            return ResponseEntity.status(HttpStatus.CREATED).body(rewardDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar recompensa: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReward(@PathVariable Long id,
                                          @Valid @RequestBody CreateRewardDTO updateRewardDTO) {
        try {
            Reward reward = rewardRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recompensa não encontrada"));

            reward.setName(updateRewardDTO.getName());
            reward.setDescription(updateRewardDTO.getDescription());
            reward.setPointsRequired(updateRewardDTO.getPointsRequired());
            reward.setType(updateRewardDTO.getType());
            reward.setAvailableQuantity(updateRewardDTO.getAvailableQuantity());
            reward.setPartnerName(updateRewardDTO.getPartnerName());
            reward.setImageUrl(updateRewardDTO.getImageUrl());
            reward.setTermsConditions(updateRewardDTO.getTermsConditions());
            reward.setExpiryDate(updateRewardDTO.getExpiryDate());

            Reward updatedReward = rewardRepository.save(reward);
            RewardDTO rewardDTO = convertToDTO(updatedReward);

            return ResponseEntity.ok(rewardDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar recompensa: " + e.getMessage());
        }
    }

    @PostMapping("/redeem")
    public ResponseEntity<?> redeemReward(@Valid @RequestBody RewardRedemptionDTO redemptionDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            UserReward userReward = rewardService.redeemReward(user.getId(), redemptionDTO.getRewardId());
            UserRewardDTO userRewardDTO = convertToUserRewardDTO(userReward);

            return ResponseEntity.status(HttpStatus.CREATED).body(new RedemptionResponse(
                    "Recompensa resgatada com sucesso", userRewardDTO));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao resgatar recompensa: " + e.getMessage());
        }
    }

    @GetMapping("/my-rewards")
    public ResponseEntity<?> getMyRewards() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            List<UserReward> userRewards = rewardService.getUserRewards(user.getId());
            List<UserRewardDTO> userRewardDTOs = userRewards.stream()
                    .map(this::convertToUserRewardDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(userRewardDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar minhas recompensas: " + e.getMessage());
        }
    }

    @PostMapping("/use/{redemptionCode}")
    public ResponseEntity<?> useReward(@PathVariable String redemptionCode) {
        try {
            UserReward userReward = rewardService.useReward(redemptionCode);
            UserRewardDTO userRewardDTO = convertToUserRewardDTO(userReward);

            return ResponseEntity.ok(new RewardUsageResponse(
                    "Recompensa utilizada com sucesso", userRewardDTO));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao utilizar recompensa: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateReward(@PathVariable Long id) {
        try {
            Reward reward = rewardRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recompensa não encontrada"));

            reward.setIsActive(false);
            rewardRepository.save(reward);

            return ResponseEntity.ok(new StatusResponse("Recompensa desativada com sucesso"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao desativar recompensa: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateReward(@PathVariable Long id) {
        try {
            Reward reward = rewardRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recompensa não encontrada"));

            reward.setIsActive(true);
            rewardRepository.save(reward);

            return ResponseEntity.ok(new StatusResponse("Recompensa ativada com sucesso"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao ativar recompensa: " + e.getMessage());
        }
    }

    private RewardDTO convertToDTO(Reward reward) {
        RewardDTO dto = new RewardDTO();
        dto.setId(reward.getId());
        dto.setName(reward.getName());
        dto.setDescription(reward.getDescription());
        dto.setPointsRequired(reward.getPointsRequired());
        dto.setType(reward.getType());
        dto.setAvailableQuantity(reward.getAvailableQuantity());
        dto.setRedeemedQuantity(reward.getRedeemedQuantity());
        dto.setPartnerName(reward.getPartnerName());
        dto.setImageUrl(reward.getImageUrl());
        dto.setTermsConditions(reward.getTermsConditions());
        dto.setExpiryDate(reward.getExpiryDate());
        dto.setIsActive(reward.getIsActive());
        dto.setCreatedAt(reward.getCreatedAt());
        return dto;
    }

    private UserRewardDTO convertToUserRewardDTO(UserReward userReward) {
        UserRewardDTO dto = new UserRewardDTO();
        dto.setId(userReward.getId());
        dto.setRewardName(userReward.getReward().getName());
        dto.setRewardDescription(userReward.getReward().getDescription());
        dto.setPointsUsed(userReward.getPointsUsed());
        dto.setStatus(userReward.getStatus());
        dto.setRedemptionCode(userReward.getRedemptionCode());
        dto.setUsedAt(userReward.getUsedAt());
        dto.setExpiresAt(userReward.getExpiresAt());
        dto.setRedeemedAt(userReward.getRedeemedAt());
        return dto;
    }

    // Classes auxiliares para respostas
    public static class RedemptionResponse {
        private String message;
        private UserRewardDTO userReward;

        public RedemptionResponse(String message, UserRewardDTO userReward) {
            this.message = message;
            this.userReward = userReward;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public UserRewardDTO getUserReward() { return userReward; }
        public void setUserReward(UserRewardDTO userReward) { this.userReward = userReward; }
    }

    public static class RewardUsageResponse {
        private String message;
        private UserRewardDTO userReward;

        public RewardUsageResponse(String message, UserRewardDTO userReward) {
            this.message = message;
            this.userReward = userReward;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public UserRewardDTO getUserReward() { return userReward; }
        public void setUserReward(UserRewardDTO userReward) { this.userReward = userReward; }
    }

    public static class StatusResponse {
        private String message;

        public StatusResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}