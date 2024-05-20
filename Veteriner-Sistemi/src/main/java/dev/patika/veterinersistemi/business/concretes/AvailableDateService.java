package dev.patika.veterinersistemi.business.concretes;

import dev.patika.veterinersistemi.business.abstracts.IAvailableDateService;
import dev.patika.veterinersistemi.core.config.exception.NotFoundException;
import dev.patika.veterinersistemi.core.utiles.Msg;
import dev.patika.veterinersistemi.dao.AvailableDateRepository;
import dev.patika.veterinersistemi.entity.Appointment;
import dev.patika.veterinersistemi.entity.AvailableDate;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateService implements IAvailableDateService {


    private final AvailableDateRepository availableDateRepo;

    public AvailableDateService(AvailableDateRepository availableDateRepo) {
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public AvailableDate save(AvailableDate availableDate) {
        return availableDateRepo.save(availableDate);
    }

    @Override
    public AvailableDate get(Long id) {
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(long id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }

    @Override
    public Appointment update(Appointment appointment) {
        return null;
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        return null;
    }

}
