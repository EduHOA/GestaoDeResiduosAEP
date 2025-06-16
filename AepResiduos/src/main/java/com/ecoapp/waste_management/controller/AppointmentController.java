package com.ecoapp.waste_management.controller;

import com.ecoapp.waste_management.dto.appointment.AppointmentDTO;
import com.ecoapp.waste_management.dto.appointment.CreateAppointmentDTO;
import com.ecoapp.waste_management.dto.appointment.UpdateAppointmentDTO;
import com.ecoapp.waste_management.entity.Appointment;
import com.ecoapp.waste_management.entity.CollectionPoint;
import com.ecoapp.waste_management.entity.User;
import com.ecoapp.waste_management.service.AppointmentService;
import com.ecoapp.waste_management.service.CollectionPointService;
import com.ecoapp.waste_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectionPointService collectionPointService;

    @PostMapping
    public ResponseEntity<?> createAppointment(@Valid @RequestBody CreateAppointmentDTO createAppointmentDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Verificar disponibilidade do slot
            if (createAppointmentDTO.getCollectionPointId() != null) {
                if (!appointmentService.isTimeSlotAvailable(
                        createAppointmentDTO.getCollectionPointId(),
                        createAppointmentDTO.getScheduledDateTime())) {
                    return ResponseEntity.badRequest()
                            .body("Horário não disponível. Escolha outro horário.");
                }
            }

            Appointment appointment = new Appointment();
            appointment.setUser(user);
            appointment.setScheduledDateTime(createAppointmentDTO.getScheduledDateTime());
            appointment.setCollectionAddress(createAppointmentDTO.getCollectionAddress());
            appointment.setWasteType(createAppointmentDTO.getWasteType());
            appointment.setEstimatedQuantity(BigDecimal.valueOf(createAppointmentDTO.getEstimatedQuantity()));
            appointment.setNotes(createAppointmentDTO.getNotes());
            appointment.setCreatedAt(LocalDateTime.now());

            if (createAppointmentDTO.getCollectionPointId() != null) {
                CollectionPoint collectionPoint = collectionPointService.getActiveCollectionPoints()
                        .stream()
                        .filter(cp -> cp.getId().equals(createAppointmentDTO.getCollectionPointId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado"));
                appointment.setCollectionPoint(collectionPoint);
            }

            Appointment savedAppointment = appointmentService.createAppointment(appointment);
            AppointmentDTO appointmentDTO = convertToDTO(savedAppointment);

            return ResponseEntity.status(HttpStatus.CREATED).body(appointmentDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar agendamento: " + e.getMessage());
        }
    }

    @GetMapping("/my-appointments")
    public ResponseEntity<?> getMyAppointments() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            List<Appointment> appointments = appointmentService.getUserAppointments(user.getId());
            List<AppointmentDTO> appointmentDTOs = appointments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(appointmentDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar agendamentos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            List<Appointment> userAppointments = appointmentService.getUserAppointments(user.getId());
            Appointment appointment = userAppointments.stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

            AppointmentDTO appointmentDTO = convertToDTO(appointment);
            return ResponseEntity.ok(appointmentDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar agendamento: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id,
                                               @Valid @RequestBody UpdateAppointmentDTO updateAppointmentDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            User user = userService.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            List<Appointment> userAppointments = appointmentService.getUserAppointments(user.getId());
            Appointment appointment = userAppointments.stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

            // Apenas permitir atualização se o agendamento estiver pendente
            if (appointment.getStatus() != com.ecoapp.waste_management.enums.AppointmentStatus.PENDING) {
                return ResponseEntity.badRequest()
                        .body("Apenas agendamentos pendentes podem ser alterados");
            }

            // Atualizar campos se fornecidos
            if (updateAppointmentDTO.getScheduledDateTime() != null) {
                appointment.setScheduledDateTime(updateAppointmentDTO.getScheduledDateTime());
            }
            if (updateAppointmentDTO.getCollectionAddress() != null) {
                appointment.setCollectionAddress(updateAppointmentDTO.getCollectionAddress());
            }
            if (updateAppointmentDTO.getEstimatedQuantity() != null) {
                appointment.setEstimatedQuantity(BigDecimal.valueOf(updateAppointmentDTO.getEstimatedQuantity()));
            }
            if (updateAppointmentDTO.getNotes() != null) {
                appointment.setNotes(updateAppointmentDTO.getNotes());
            }

            Appointment updatedAppointment = appointmentService.createAppointment(appointment);
            AppointmentDTO appointmentDTO = convertToDTO(updatedAppointment);

            return ResponseEntity.ok(appointmentDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar agendamento: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<?> confirmAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.confirmAppointment(id);
            AppointmentDTO appointmentDTO = convertToDTO(appointment);
            return ResponseEntity.ok(appointmentDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao confirmar agendamento: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(@PathVariable Long id,
                                                 @RequestParam Double actualQuantity) {
        try {
            Appointment appointment = appointmentService.completeAppointment(id, actualQuantity);
            AppointmentDTO appointmentDTO = convertToDTO(appointment);
            return ResponseEntity.ok(appointmentDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao completar agendamento: " + e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getPendingAppointments();
            List<AppointmentDTO> appointmentDTOs = appointments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(appointmentDTOs);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar agendamentos pendentes: " + e.getMessage());
        }
    }

    @GetMapping("/check-availability")
    public ResponseEntity<?> checkTimeSlotAvailability(@RequestParam Long collectionPointId,
                                                       @RequestParam String dateTime) {
        try {
            LocalDateTime scheduledDateTime = LocalDateTime.parse(dateTime);
            boolean isAvailable = appointmentService.isTimeSlotAvailable(collectionPointId, scheduledDateTime);

            return ResponseEntity.ok(new TimeSlotAvailabilityResponse(isAvailable,
                    isAvailable ? "Horário disponível" : "Horário não disponível"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao verificar disponibilidade: " + e.getMessage());
        }
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setUserName(appointment.getUser().getName());
        dto.setScheduledDateTime(appointment.getScheduledDateTime());
        dto.setCollectionAddress(appointment.getCollectionAddress());
        dto.setWasteType(appointment.getWasteType());
        dto.setEstimatedQuantity(appointment.getEstimatedQuantity() != null ? appointment.getEstimatedQuantity().doubleValue() : null);
        dto.setActualQuantity(appointment.getActualQuantity() != null ? appointment.getActualQuantity().doubleValue() : null);
        dto.setPointsEarned(appointment.getPointsEarned());
        dto.setStatus(appointment.getStatus());
        dto.setNotes(appointment.getNotes());
        dto.setCompletedAt(appointment.getCompletedAt());
        dto.setCreatedAt(appointment.getCreatedAt());

        if (appointment.getCollectionPoint() != null) {
            dto.setCollectionPointName(appointment.getCollectionPoint().getName());
        }

        return dto;
    }

    // Classe auxiliar para resposta de disponibilidade
    public static class TimeSlotAvailabilityResponse {
        private boolean available;
        private String message;

        public TimeSlotAvailabilityResponse(boolean available, String message) {
            this.available = available;
            this.message = message;
        }

        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}