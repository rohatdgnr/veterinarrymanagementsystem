package dev.patika.veterinersistemi.dao;

import dev.patika.veterinersistemi.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine,Long> {
    List<Vaccine> findByAnimalId(Long animalId);
    List<Vaccine> findByAnimalIdAndProtectionStartDateBetween(Long animalId, LocalDate startDate, LocalDate endDate);
    List<Vaccine> findByProtectionStartDateBetween(LocalDate startDate, LocalDate endDate);

}

