package com.ecoapp.waste_management.service;

import com.ecoapp.waste_management.entity.User;
import com.ecoapp.waste_management.enums.UserLevel;
import com.ecoapp.waste_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUserPoints(Long userId, int pointsToAdd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setPoints(user.getPoints() + pointsToAdd);
        user.setLevel(UserLevel.getByPoints(user.getPoints()));

        return userRepository.save(user);
    }

    public boolean deductPoints(Long userId, int pointsToDeduct) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user.getPoints() >= pointsToDeduct) {
            user.setPoints(user.getPoints() - pointsToDeduct);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public List<User> getTopUsers() {
        return userRepository.findTopUsersByPoints(0);
    }
}