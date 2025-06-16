package com.ecoapp.waste_management.repository;

import com.ecoapp.waste_management.entity.Appointment;
import com.ecoapp.waste_management.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId(Long userId);
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByScheduledDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.status = :status")
    List<Appointment> findByUserIdAndStatus(Long userId, AppointmentStatus status);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.collectionPoint.id = :collectionPointId AND a.scheduledDateTime BETWEEN :start AND :end")
    Long countAppointmentsByCollectionPointAndDateRange(Long collectionPointId, LocalDateTime start, LocalDateTime end);
}