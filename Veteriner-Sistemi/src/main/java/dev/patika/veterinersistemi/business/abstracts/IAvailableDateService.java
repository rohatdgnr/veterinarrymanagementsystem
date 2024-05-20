package dev.patika.veterinersistemi.business.abstracts;

import dev.patika.veterinersistemi.entity.Animal;
import dev.patika.veterinersistemi.entity.Appointment;
import dev.patika.veterinersistemi.entity.AvailableDate;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);
    AvailableDate get(Long id);

    AvailableDate update (AvailableDate availableDate);
    boolean delete(long id);

    Appointment update(Appointment appointment);
}