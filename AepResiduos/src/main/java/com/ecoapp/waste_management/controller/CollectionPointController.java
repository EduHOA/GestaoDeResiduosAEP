package com.ecoapp.waste_management.controller;

import com.ecoapp.waste_management.dto.collectionpoint.CollectionPointDTO;
import com.ecoapp.waste_management.dto.collectionpoint.CreateCollectionPointDTO;
import com.ecoapp.waste_management.entity.CollectionPoint;
import com.ecoapp.waste_management.entity.CollectionPointWaste;
import com.ecoapp.waste_management.entity.Waste;
import com.ecoapp.waste_management.enums.CollectionPointStatus;
import com.ecoapp.waste_management.service.CollectionPointService;
import com.ecoapp.waste_management.repository.CollectionPointRepository;
import com.ecoapp.waste_management.repository.WasteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/collection-points")
@CrossOrigin(origins = "*")
public class CollectionPointController {

    @Autowired
    private CollectionPointService collectionPointService;

    @Autowired
    private CollectionPointRepository collectionPointRepository;

    @Autowired
    private WasteRepository wasteRepository;

    @GetMapping
    public ResponseEntity<?> getAllCollectionPoints() {
        try {
            List<CollectionPoint> collectionPoints = collectionPointService.getActiveCollectionPoints();
            List<CollectionPointDTO> collectionPointDTOs = collectionPoints.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(collectionPointDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar pontos de coleta: " + e.getMessage());
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyCollectionPoints(@RequestParam Double latitude,
                                                       @RequestParam Double longitude,
                                                       @RequestParam(defaultValue = "10.0") Double radiusKm) {
        try {
            List<CollectionPoint> collectionPoints = collectionPointService
                    .getNearbyCollectionPoints(latitude, longitude, radiusKm);
            List<CollectionPointDTO> collectionPointDTOs = collectionPoints.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(collectionPointDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar pontos próximos: " + e.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableCollectionPoints() {
        try {
            List<CollectionPoint> collectionPoints = collectionPointService.getAvailableCollectionPoints();
            List<CollectionPointDTO> collectionPointDTOs = collectionPoints.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(collectionPointDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar pontos disponíveis: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectionPoint(@PathVariable Long id) {
        try {
            CollectionPoint collectionPoint = collectionPointRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado"));

            CollectionPointDTO collectionPointDTO = convertToDTO(collectionPoint);
            return ResponseEntity.ok(collectionPointDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar ponto de coleta: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createCollectionPoint(@Valid @RequestBody CreateCollectionPointDTO createCollectionPointDTO) {
        try {
            CollectionPoint collectionPoint = new CollectionPoint();
            collectionPoint.setName(createCollectionPointDTO.getName());
            collectionPoint.setAddress(createCollectionPointDTO.getAddress());
            collectionPoint.setLatitude(BigDecimal.valueOf(createCollectionPointDTO.getLatitude()));
            collectionPoint.setLongitude(BigDecimal.valueOf(createCollectionPointDTO.getLongitude()));
            collectionPoint.setPhone(createCollectionPointDTO.getPhone());
            collectionPoint.setOpeningHours(createCollectionPointDTO.getOpeningHours());
            collectionPoint.setCapacity(createCollectionPointDTO.getCapacity());
            collectionPoint.setCurrentLoad(0);
            collectionPoint.setStatus(CollectionPointStatus.ACTIVE);
            collectionPoint.setCreatedAt(LocalDateTime.now());

            // Associar tipos de resíduos aceitos (CollectionPointWaste)
            if (createCollectionPointDTO.getAcceptedWasteIds() != null) {
                List<Waste> wastes = wasteRepository.findAllById(createCollectionPointDTO.getAcceptedWasteIds());

                List<CollectionPointWaste> collectionPointWastes = wastes.stream()
                        .map(waste -> {
                            CollectionPointWaste cpw = new CollectionPointWaste();
                            cpw.setCollectionPoint(collectionPoint);
                            cpw.setWaste(waste);
                            // Se quiser, inicialize maxCapacityKg e currentLoadKg aqui
                            cpw.setMaxCapacityKg(BigDecimal.ZERO); // ou algum valor padrão
                            cpw.setCurrentLoadKg(BigDecimal.ZERO);
                            return cpw;
                        })
                        .collect(Collectors.toList());

                collectionPoint.setAcceptedWastes(collectionPointWastes);
            }

            CollectionPoint savedCollectionPoint = collectionPointRepository.save(collectionPoint);
            CollectionPointDTO collectionPointDTO = convertToDTO(savedCollectionPoint);

            return ResponseEntity.status(HttpStatus.CREATED).body(collectionPointDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar ponto de coleta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCollectionPoint(@PathVariable Long id,
                                                   @Valid @RequestBody CreateCollectionPointDTO updateCollectionPointDTO) {
        try {
            CollectionPoint collectionPoint = collectionPointRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado"));

            collectionPoint.setName(updateCollectionPointDTO.getName());
            collectionPoint.setAddress(updateCollectionPointDTO.getAddress());
            collectionPoint.setLatitude(BigDecimal.valueOf(updateCollectionPointDTO.getLatitude()));
            collectionPoint.setLongitude(BigDecimal.valueOf(updateCollectionPointDTO.getLongitude()));
            collectionPoint.setPhone(updateCollectionPointDTO.getPhone());
            collectionPoint.setOpeningHours(updateCollectionPointDTO.getOpeningHours());
            collectionPoint.setCapacity(updateCollectionPointDTO.getCapacity());

            // Atualizar tipos de resíduos aceitos
            if (updateCollectionPointDTO.getAcceptedWasteIds() != null) {
                List<Waste> wastes = wasteRepository.findAllById(updateCollectionPointDTO.getAcceptedWasteIds());

                List<CollectionPointWaste> collectionPointWastes = wastes.stream()
                        .map(waste -> {
                            CollectionPointWaste cpw = new CollectionPointWaste();
                            cpw.setCollectionPoint(collectionPoint);
                            cpw.setWaste(waste);
                            cpw.setMaxCapacityKg(BigDecimal.ZERO);
                            cpw.setCurrentLoadKg(BigDecimal.ZERO);
                            return cpw;
                        })
                        .collect(Collectors.toList());

                collectionPoint.setAcceptedWastes(collectionPointWastes);
            }

            CollectionPoint updatedCollectionPoint = collectionPointRepository.save(collectionPoint);
            CollectionPointDTO collectionPointDTO = convertToDTO(updatedCollectionPoint);

            return ResponseEntity.ok(collectionPointDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar ponto de coleta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/capacity")
    public ResponseEntity<?> updateCapacity(@PathVariable Long id,
                                            @RequestParam Integer newLoad) {
        try {
            CollectionPoint collectionPoint = collectionPointService.updateCapacity(id, newLoad);
            CollectionPointDTO collectionPointDTO = convertToDTO(collectionPoint);

            return ResponseEntity.ok(collectionPointDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar capacidade: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestParam CollectionPointStatus status) {
        try {
            CollectionPoint collectionPoint = collectionPointRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado"));

            collectionPoint.setStatus(status);
            CollectionPoint updatedCollectionPoint = collectionPointRepository.save(collectionPoint);
            CollectionPointDTO collectionPointDTO = convertToDTO(updatedCollectionPoint);

            return ResponseEntity.ok(collectionPointDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar status: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateCollectionPoint(@PathVariable Long id) {
        try {
            CollectionPoint collectionPoint = collectionPointRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado"));

            collectionPoint.setStatus(CollectionPointStatus.INACTIVE);
            collectionPointRepository.save(collectionPoint);

            return ResponseEntity.ok(new StatusResponse("Ponto de coleta desativado com sucesso"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao desativar ponto de coleta: " + e.getMessage());
        }
    }

    private CollectionPointDTO convertToDTO(CollectionPoint collectionPoint) {
        CollectionPointDTO dto = new CollectionPointDTO();
        dto.setId(collectionPoint.getId());
        dto.setName(collectionPoint.getName());
        dto.setAddress(collectionPoint.getAddress());
        dto.setLatitude(collectionPoint.getLatitude() != null ? collectionPoint.getLatitude().doubleValue() : null);
        dto.setLongitude(collectionPoint.getLongitude() != null ? collectionPoint.getLongitude().doubleValue() : null);
        dto.setPhone(collectionPoint.getPhone());
        dto.setOpeningHours(collectionPoint.getOpeningHours());
        dto.setCapacity(collectionPoint.getCapacity());
        dto.setCurrentLoad(collectionPoint.getCurrentLoad());
        dto.setStatus(collectionPoint.getStatus());
        dto.setCreatedAt(collectionPoint.getCreatedAt());

        if (collectionPoint.getAcceptedWastes() != null) {
            List<String> acceptedWasteTypes = collectionPoint.getAcceptedWastes().stream()
                    .map(cpw -> cpw.getWaste().getName())
                    .collect(Collectors.toList());
            dto.setAcceptedWasteTypes(acceptedWasteTypes);
        }

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