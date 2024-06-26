package dev.patika.veterinersistemi.business.concretes;

import dev.patika.veterinersistemi.business.abstracts.IVaccineService;
import dev.patika.veterinersistemi.core.config.exeption.NotFoundException;
import dev.patika.veterinersistemi.core.config.utiles.Msg;
import dev.patika.veterinersistemi.dao.AnimalRepository;
import dev.patika.veterinersistemi.dao.VaccineRepository;
import dev.patika.veterinersistemi.entity.Animal;
import dev.patika.veterinersistemi.entity.Vaccine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaccineService implements IVaccineService {

    private final VaccineRepository vaccineRepo;
    private final AnimalRepository animalRepo;
    @Override
    public Vaccine save(Vaccine vaccine, Long animalId) {
       log.info("vaccine : {},animalId : {}",vaccine,animalId);
        Animal animal = animalRepo.findById(animalId)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        vaccine.setAnimal(animal);
        log.info("animal : {}",animal);
        return vaccineRepo.save(vaccine);
    }


    @Override
    public Vaccine get(Long id) {
        return this.vaccineRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }


    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.vaccineRepo.findAll(pageable);
    }


    @Override
    public Vaccine update(Vaccine vaccine) {
        this.get(vaccine.getId());
        return this.vaccineRepo.save(vaccine);
    }


    @Override
    public boolean delete(long id) {
        Vaccine vaccine = this.get(id);
        this.vaccineRepo.delete(vaccine);
        return true;
    }


    @Override
    public List<Vaccine> getVaccinesByAnimalId(Long animalId) {
        return vaccineRepo.findByAnimalId(animalId);
    }

    @Override
    public List<Vaccine> getVaccinesByDateRangeForAnimal(Long animalId, LocalDate startDate, LocalDate endDate) {
        return vaccineRepo.findByAnimalIdAndProtectionStartDateBetween(animalId,startDate,endDate);
    }

    @Override
    public List<Vaccine> getVaccinesByDateRange(LocalDate startDate, LocalDate endDate) {
        return vaccineRepo.findByProtectionStartDateBetween(startDate, endDate);
    }

    @Override
    public List<Vaccine> getByProtectionStartDateBetween(LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate,Msg.NOT_FOUND);
        Objects.requireNonNull(endDate, Msg.NOT_FOUND);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(Msg.HISTORY_CONTROLLER);
        }

        return vaccineRepo.findByProtectionStartDateBetween(startDate, endDate);

    }

    @Override
    public List<Vaccine> findByAnimalIdAndProtectionStartDateBetween(Long animalId, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(animalId, Msg.NOT_FOUND);
        Objects.requireNonNull(startDate, Msg.NOT_FOUND);
        Objects.requireNonNull(endDate, Msg.NOT_FOUND);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(Msg.HISTORY_CONTROLLER);
        }
        return vaccineRepo.findByAnimalIdAndProtectionStartDateBetween(animalId, startDate, endDate);

    }
@Override
    public boolean existsActiveVaccineByAnimalIdAndVaccineCode(Long animalId, String vaccineCode) {

        return vaccineRepo.existsByAnimalIdAndCodeAndProtectionFinishDateAfter(animalId, vaccineCode, LocalDate.now());
    }


}
