package dev.patika.veterinersistemi.business.abstracts;

import dev.patika.veterinersistemi.entity.Doctor;
import org.springframework.data.domain.Page;

public interface IDoctorService {
    Doctor save(Doctor doctor);
    Doctor get(Long id);
    Page<Doctor> cursor(int page, int pageSize);
    Doctor update (Doctor doctor);
    boolean delete (long id);
}