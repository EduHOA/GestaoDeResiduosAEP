package com.ecoapp.waste_management.dto.user;

import com.ecoapp.waste_management.enums.UserLevel;
import com.ecoapp.waste_management.enums.UserStatus;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer points;
    private UserLevel level;
    private UserStatus status;
    private LocalDateTime createdAt;

    // Construtores
    public UserDTO() {}

    public UserDTO(Long id, String name, String email, Integer points, UserLevel level) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.points = points;
        this.level = level;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public UserLevel getLevel() { return level; }
    public void setLevel(UserLevel level) { this.level = level; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}