package dev.patika.veterinersistemi.business.abstracts;



import dev.patika.veterinersistemi.entity.AvailableDate;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);
    AvailableDate get(Long id);

    boolean delete(long id);

    AvailableDate update(AvailableDate availableDate);

    boolean isDoctorAvailableOnDate(Long doctorId, LocalDate date);
}