package dev.patika.veterinersistemi.dao;



import dev.patika.veterinersistemi.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findByAnimalId(Long animalId);

    // Doktor ID'sine göre randevuları bulma
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
  //  List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> findByDoctorIdAndAppointmentDateTime(Long doctorId, LocalDateTime dateTime);
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDateTime >= :startDate AND a.appointmentDateTime <= :endDate AND a.doctor.id = :doctorId")
    List<Appointment> findByDateRangeAndDoctorId(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate,
                                                 @Param("doctorId") Long doctorId);


    @Query("SELECT a FROM Appointment a WHERE a.appointmentDateTime >= :startDate AND a.appointmentDateTime <= :endDate" +
            " AND (:animalId IS NULL OR a.animal.id = :animalId)")
    List<Appointment> findByDateRangeAndAnimalId(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate,
                                                 @Param("animalId") Long animalId);
}


