package dev.patika.veterinersistemi.business.abstracts;



import dev.patika.veterinersistemi.entity.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {

    // Aşıyı kaydetmek için
    Vaccine save(Vaccine vaccine, Long animalId);

    Vaccine get(Long id);
    Page<Vaccine> cursor(int page, int pageSize);
    Vaccine update (Vaccine vaccine);
    boolean delete (long id);
    List<Vaccine> getVaccinesByAnimalId(Long animalId);

    // Belirli bir tarih aralığındaki aşıları getirmek için
    List<Vaccine> getVaccinesByDateRangeForAnimal(Long animalId, LocalDate startDate, LocalDate endDate);
    //Aşı tarihine ve Animal ıd sine göre sorgulama
    List<Vaccine> findByAnimalIdAndProtectionStartDateBetween(Long animalId, LocalDate startDate, LocalDate endDate);

    List<Vaccine> getVaccinesByDateRange(LocalDate startDate, LocalDate endDate);
    List<Vaccine> getByProtectionStartDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsActiveVaccineByAnimalIdAndVaccineCode(Long animalId, String vaccineCode);
}
