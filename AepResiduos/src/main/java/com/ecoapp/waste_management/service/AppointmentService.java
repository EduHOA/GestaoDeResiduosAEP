package com.ecoapp.waste_management.service;

import com.ecoapp.waste_management.entity.Appointment;
import com.ecoapp.waste_management.entity.User;
import com.ecoapp.waste_management.entity.Waste;
import com.ecoapp.waste_management.enums.AppointmentStatus;
import com.ecoapp.waste_management.enums.WasteType;
import com.ecoapp.waste_management.repository.AppointmentRepository;
import com.ecoapp.waste_management.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private WasteRepository wasteRepository;

    @Autowired
    private UserService userService;

    public Appointment createAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentRepository.save(appointment);
    }

    public Appointment confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

    public Appointment completeAppointment(Long appointmentId, Double actualQuantity) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        appointment.setActualQuantity(BigDecimal.valueOf(actualQuantity));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setCompletedAt(LocalDateTime.now());

        // Calcular pontos baseado no tipo de resíduo e quantidade
        int pointsEarned = calculatePoints(appointment.getWasteType(), actualQuantity);
        appointment.setPointsEarned(pointsEarned);

        // Atualizar pontos do usuário
        userService.updateUserPoints(appointment.getUser().getId(), pointsEarned);

        return appointmentRepository.save(appointment);
    }

    private int calculatePoints(WasteType wasteType, Double quantity) {
        if (quantity == null || quantity <= 0) return 0;
        return (int) (wasteType.getDefaultPointsPerKg() * quantity);
    }

    public List<Appointment> getUserAppointments(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> getPendingAppointments() {
        return appointmentRepository.findByStatus(AppointmentStatus.PENDING);
    }

    public boolean isTimeSlotAvailable(Long collectionPointId, LocalDateTime dateTime) {
        LocalDateTime start = dateTime.minusMinutes(30);
        LocalDateTime end = dateTime.plusMinutes(30);

        Long count = appointmentRepository.countAppointmentsByCollectionPointAndDateRange(
                collectionPointId, start, end);

        return count < 3; // Máximo 3 agendamentos por slot de 1 hora
    }
}