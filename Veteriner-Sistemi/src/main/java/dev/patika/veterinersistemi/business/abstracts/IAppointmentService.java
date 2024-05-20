package dev.patika.veterinersistemi.business.abstracts;

import dev.patika.veterinersistemi.entity.Appointment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAppointmentService {
    Appointment save(Appointment appointment);
    Appointment get(Long id);
    Page<Appointment> cursor(int page, int pageSize);
    Appointment update (Appointment appointment);
    boolean delete (long id);

    List<Appointment> getByDoctorId(Long doctorId);
    List<Appointment> getByAnimalId(Long animalId);

    List<Appointment> getAppointmentsByDateRange(String startDate, String endDate);

}
