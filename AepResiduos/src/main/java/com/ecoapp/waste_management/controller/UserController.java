package com.ecoapp.waste_management.controller;

import com.ecoapp.waste_management.dto.user.UserDTO;
import com.ecoapp.waste_management.dto.user.UserRewardDTO;
import com.ecoapp.waste_management.entity.User;
import com.ecoapp.waste_management.entity.UserReward;
import com.ecoapp.waste_management.enums.UserStatus;
import com.ecoapp.waste_management.repository.UserRepository;
import com.ecoapp.waste_management.service.RewardService;
import com.ecoapp.waste_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RewardService rewardService;

    @GetMapping("/ranking")
    public ResponseEntity<?> getUserRanking() {
        try {
            List<User> topUsers = userService.getTopUsers();
            List<UserDTO> userDTOs = topUsers.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(userDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar ranking de usuários: " + e.getMessage());
        }
    }

    @GetMapping("/profile/points")
    public ResponseEntity<?> getUserPoints() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            UserPointsResponse response = new UserPointsResponse(
                    user.getPoints(),
                    user.getLevel(),
                    calculateNextLevelPoints(user.getPoints())
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar pontos do usuário: " + e.getMessage());
        }
    }

    @GetMapping("/profile/history")
    public ResponseEntity<?> getUserHistory() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Buscar histórico de recompensas do usuário
            List<UserReward> userRewards = rewardService.getUserRewards(user.getId());
            List<UserRewardDTO> userRewardDTOs = userRewards.stream()
                    .map(this::convertToUserRewardDTO)
                    .collect(Collectors.toList());

            UserHistoryResponse response = new UserHistoryResponse(
                    user.getPoints(),
                    userRewardDTOs
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar histórico do usuário: " + e.getMessage());
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getUserStats() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Buscar estatísticas do usuário
            List<UserReward> userRewards = rewardService.getUserRewards(user.getId());

            UserStatsResponse stats = new UserStatsResponse(
                    user.getPoints(),
                    user.getLevel(),
                    userRewards.size(),
                    calculateUserRanking(user.getId())
            );

            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar estatísticas do usuário: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id,
                                              @RequestParam UserStatus status) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            user.setStatus(status);
            User updatedUser = userRepository.save(user);
            UserDTO userDTO = convertToDTO(updatedUser);

            return ResponseEntity.ok(userDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar status do usuário: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/add-points")
    public ResponseEntity<?> addPointsToUser(@PathVariable Long id,
                                             @RequestParam Integer points) {
        try {
            User user = userService.updateUserPoints(id, points);
            UserDTO userDTO = convertToDTO(user);

            return ResponseEntity.ok(new PointsUpdateResponse(
                    "Pontos adicionados com sucesso", userDTO));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar pontos: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam String query) {
        try {
            List<User> users = userRepository.findByNameContainingIgnoreCase(query);
            List<UserDTO> userDTOs = users.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(userDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveUsers() {
        try {
            List<User> users = userRepository.findByStatus(UserStatus.ACTIVE);
            List<UserDTO> userDTOs = users.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(userDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar usuários ativos: " + e.getMessage());
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setPoints(user.getPoints());
        dto.setLevel(user.getLevel());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
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

    private Integer calculateNextLevelPoints(Integer currentPoints) {
        // Lógica para calcular pontos necessários para o próximo nível
        // Isso depende da implementação dos níveis do usuário
        if (currentPoints < 100) return 100 - currentPoints;
        if (currentPoints < 500) return 500 - currentPoints;
        if (currentPoints < 1000) return 1000 - currentPoints;
        if (currentPoints < 2500) return 2500 - currentPoints;
        return 0; // Nível máximo atingido
    }

    private Integer calculateUserRanking(Long userId) {
        List<User> topUsers = userService.getTopUsers();
        for (int i = 0; i < topUsers.size(); i++) {
            if (topUsers.get(i).getId().equals(userId)) {
                return i + 1;
            }
        }
        return topUsers.size() + 1;
    }

    // Classes auxiliares para respostas
    public static class UserPointsResponse {
        private Integer currentPoints;
        private com.ecoapp.waste_management.enums.UserLevel level;
        private Integer pointsToNextLevel;

        public UserPointsResponse(Integer currentPoints, com.ecoapp.waste_management.enums.UserLevel level, Integer pointsToNextLevel) {
            this.currentPoints = currentPoints;
            this.level = level;
            this.pointsToNextLevel = pointsToNextLevel;
        }

        public Integer getCurrentPoints() { return currentPoints; }
        public void setCurrentPoints(Integer currentPoints) { this.currentPoints = currentPoints; }
        public com.ecoapp.waste_management.enums.UserLevel getLevel() { return level; }
        public void setLevel(com.ecoapp.waste_management.enums.UserLevel level) { this.level = level; }
        public Integer getPointsToNextLevel() { return pointsToNextLevel; }
        public void setPointsToNextLevel(Integer pointsToNextLevel) { this.pointsToNextLevel = pointsToNextLevel; }
    }

    public static class UserHistoryResponse {
        private Integer totalPoints;
        private List<UserRewardDTO> rewards;

        public UserHistoryResponse(Integer totalPoints, List<UserRewardDTO> rewards) {
            this.totalPoints = totalPoints;
            this.rewards = rewards;
        }

        public Integer getTotalPoints() { return totalPoints; }
        public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
        public List<UserRewardDTO> getRewards() { return rewards; }
        public void setRewards(List<UserRewardDTO> rewards) { this.rewards = rewards; }
    }

    public static class UserStatsResponse {
        private Integer totalPoints;
        private com.ecoapp.waste_management.enums.UserLevel level;
        private Integer totalRedemptions;
        private Integer ranking;

        public UserStatsResponse(Integer totalPoints, com.ecoapp.waste_management.enums.UserLevel level, Integer totalRedemptions, Integer ranking) {
            this.totalPoints = totalPoints;
            this.level = level;
            this.totalRedemptions = totalRedemptions;
            this.ranking = ranking;
        }

        public Integer getTotalPoints() { return totalPoints; }
        public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
        public com.ecoapp.waste_management.enums.UserLevel getLevel() { return level; }
        public void setLevel(com.ecoapp.waste_management.enums.UserLevel level) { this.level = level; }
        public Integer getTotalRedemptions() { return totalRedemptions; }
        public void setTotalRedemptions(Integer totalRedemptions) { this.totalRedemptions = totalRedemptions; }
        public Integer getRanking() { return ranking; }
        public void setRanking(Integer ranking) { this.ranking = ranking; }
    }

    public static class PointsUpdateResponse {
        private String message;
        private UserDTO user;

        public PointsUpdateResponse(String message, UserDTO user) {
            this.message = message;
            this.user = user;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public UserDTO getUser() { return user; }
        public void setUser(UserDTO user) { this.user = user; }
    }
}