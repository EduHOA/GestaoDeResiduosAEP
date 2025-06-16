package com.ecoapp.waste_management.controller;

import com.ecoapp.waste_management.dto.waste.WasteDTO;
import com.ecoapp.waste_management.entity.Waste;
import com.ecoapp.waste_management.enums.WasteCategory;
import com.ecoapp.waste_management.enums.WasteType;
import com.ecoapp.waste_management.repository.WasteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wastes")
@CrossOrigin(origins = "*")
public class WasteController {

    @Autowired
    private WasteRepository wasteRepository;

    @GetMapping
    public ResponseEntity<?> getAllWastes() {
        try {
            List<Waste> wastes = wasteRepository.findAll();
            List<WasteDTO> wasteDTOs = wastes.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(wasteDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar tipos de resíduos: " + e.getMessage());
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getWastesByCategory(@PathVariable WasteCategory category) {
        try {
            List<Waste> wastes = wasteRepository.findByCategory(category);
            List<WasteDTO> wasteDTOs = wastes.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(wasteDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar resíduos por categoria: " + e.getMessage());
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getWastesByType(@PathVariable WasteType type) {
        try {
            List<Waste> wastes = wasteRepository.findByType(type);
            List<WasteDTO> wasteDTOs = wastes.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(wasteDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar resíduos por tipo: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWaste(@PathVariable Long id) {
        try {
            Waste waste = wasteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tipo de resíduo não encontrado"));

            WasteDTO wasteDTO = convertToDTO(waste);
            return ResponseEntity.ok(wasteDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar tipo de resíduo: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWaste(@PathVariable Long id) {
        try {
            Waste waste = wasteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tipo de resíduo não encontrado"));

            wasteRepository.deleteById(id);
            return ResponseEntity.ok(new StatusResponse("Tipo de resíduo removido com sucesso"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover tipo de resíduo: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchWastes(@RequestParam String query) {
        try {
            List<Waste> wastes = wasteRepository.findByNameContainingIgnoreCase(query);
            List<WasteDTO> wasteDTOs = wastes.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(wasteDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar resíduos: " + e.getMessage());
        }
    }

    private WasteDTO convertToDTO(Waste waste) {
        WasteDTO dto = new WasteDTO();
        dto.setId(waste.getId());
        dto.setName(waste.getName());
        dto.setType(waste.getType());
        dto.setCategory(waste.getCategory());
        dto.setPointsPerKg(waste.getPointsPerKg());
        dto.setInstructions(waste.getInstructions());
        dto.setDescription(waste.getDescription());
        dto.setImageUrl(waste.getImageUrl());
        dto.setCreatedAt(waste.getCreatedAt());
        return dto;
    }

    // Classe auxiliar para resposta de status
    public static class StatusResponse {
        private String message;

        public StatusResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}