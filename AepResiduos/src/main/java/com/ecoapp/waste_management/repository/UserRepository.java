package com.ecoapp.waste_management.repository;

import com.ecoapp.waste_management.entity.User;
import com.ecoapp.waste_management.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByStatus(UserStatus status);

    @Query("SELECT u FROM User u WHERE u.points >= :minPoints ORDER BY u.points DESC")
    List<User> findTopUsersByPoints(int minPoints);

    List<User> findByNameContainingIgnoreCase(String query);
}