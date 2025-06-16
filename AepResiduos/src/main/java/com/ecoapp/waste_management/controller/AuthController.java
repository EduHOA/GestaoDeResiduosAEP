package com.ecoapp.waste_management.controller;

import com.ecoapp.waste_management.dto.auth.CreateUserDTO;
import com.ecoapp.waste_management.dto.auth.LoginDTO;
import com.ecoapp.waste_management.dto.user.UserDTO;
import com.ecoapp.waste_management.entity.User;
import com.ecoapp.waste_management.enums.UserLevel;
import com.ecoapp.waste_management.enums.UserStatus;
import com.ecoapp.waste_management.service.UserService;
import com.ecoapp.waste_management.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            // Verificar se o email já existe
            Optional<User> existingUser = userService.findByEmail(createUserDTO.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest()
                        .body("Email já está em uso");
            }

            // Criar novo usuário
            User user = new User();
            user.setName(createUserDTO.getName());
            user.setEmail(createUserDTO.getEmail());
            user.setPassword(createUserDTO.getPassword());
            user.setPhone(createUserDTO.getPhone());
            user.setAddress(createUserDTO.getAddress());
            user.setPoints(0);
            user.setLevel(UserLevel.BRONZE);
            user.setStatus(UserStatus.ACTIVE);
            user.setCreatedAt(LocalDateTime.now());

            User savedUser = userService.createUser(user);
            UserDTO userDTO = convertToUserDTO(savedUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(new UserRegistrationResponse(
                    "Usuário criado com sucesso", userDTO));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            // Autenticar usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );

            // Buscar usuário no banco
            User user = userService.findByEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Verificar se o usuário está ativo
            if (user.getStatus() != UserStatus.ACTIVE) {
                return ResponseEntity.badRequest()
                        .body("Conta desativada. Entre em contato com o suporte.");
            }

            // Gerar token JWT
            String token = jwtUtil.generateToken(user.getEmail());
            UserDTO userDTO = convertToUserDTO(user);

            return ResponseEntity.ok(new LoginResponse(
                    "Login realizado com sucesso",
                    token,
                    userDTO
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Credenciais inválidas");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            UserDTO userDTO = convertToUserDTO(user);
            return ResponseEntity.ok(userDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao buscar perfil: " + e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateUserProfileDTO updateUserProfileDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Atualizar campos se fornecidos
            if (updateUserProfileDTO.getName() != null) {
                user.setName(updateUserProfileDTO.getName());
            }
            if (updateUserProfileDTO.getPhone() != null) {
                user.setPhone(updateUserProfileDTO.getPhone());
            }
            if (updateUserProfileDTO.getAddress() != null) {
                user.setAddress(updateUserProfileDTO.getAddress());
            }

            User updatedUser = userService.createUser(user);
            UserDTO userDTO = convertToUserDTO(updatedUser);

            return ResponseEntity.ok(new ProfileUpdateResponse(
                    "Perfil atualizado com sucesso", userDTO));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao atualizar perfil: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        try {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(new LogoutResponse("Logout realizado com sucesso"));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao fazer logout: " + e.getMessage());
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractEmail(token);

                if (email != null && jwtUtil.validateToken(token)) {
                    User user = userService.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                    UserDTO userDTO = convertToUserDTO(user);
                    return ResponseEntity.ok(new TokenValidationResponse(true, userDTO));
                }
            }

            return ResponseEntity.ok(new TokenValidationResponse(false, null));

        } catch (Exception e) {
            return ResponseEntity.ok(new TokenValidationResponse(false, null));
        }
    }

    private UserDTO convertToUserDTO(User user) {
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

    // Classes auxiliares para respostas
    public static class UserRegistrationResponse {
        private String message;
        private UserDTO user;

        public UserRegistrationResponse(String message, UserDTO user) {
            this.message = message;
            this.user = user;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public UserDTO getUser() { return user; }
        public void setUser(UserDTO user) { this.user = user; }
    }

    public static class LoginResponse {
        private String message;
        private String token;
        private UserDTO user;

        public LoginResponse(String message, String token, UserDTO user) {
            this.message = message;
            this.token = token;
            this.user = user;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public UserDTO getUser() { return user; }
        public void setUser(UserDTO user) { this.user = user; }
    }

    public static class ProfileUpdateResponse {
        private String message;
        private UserDTO user;

        public ProfileUpdateResponse(String message, UserDTO user) {
            this.message = message;
            this.user = user;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public UserDTO getUser() { return user; }
        public void setUser(UserDTO user) { this.user = user; }
    }

    public static class LogoutResponse {
        private String message;

        public LogoutResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    public static class TokenValidationResponse {
        private boolean valid;
        private UserDTO user;

        public TokenValidationResponse(boolean valid, UserDTO user) {
            this.valid = valid;
            this.user = user;
        }

        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }
        public UserDTO getUser() { return user; }
        public void setUser(UserDTO user) { this.user = user; }
    }

    // DTO auxiliar para atualização de perfil
    public static class UpdateUserProfileDTO {
        private String name;
        private String phone;
        private String address;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }
}