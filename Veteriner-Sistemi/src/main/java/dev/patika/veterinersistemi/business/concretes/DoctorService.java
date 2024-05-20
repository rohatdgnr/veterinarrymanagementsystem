package dev.patika.veterinersistemi.business.concretes;

import dev.patika.veterinersistemi.core.config.exception.NotFoundException;
import dev.patika.veterinersistemi.core.utiles.Msg;
import dev.patika.veterinersistemi.dao.DoctorRepository;
import dev.patika.veterinersistemi.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import dev.patika.veterinersistemi.business.abstracts.IDoctorService;
@Service
public class DoctorService implements IDoctorService {
    // DoctorRepo bağımlılığını enjekte etmek için constructor
    private final DoctorRepository doctorRepo;

    // Constructor enjeksiyonu
    public DoctorService(DoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    // Doktoru kaydetmek için
    @Override
    public Doctor save(Doctor doctor) {
        return this.doctorRepo.save(doctor); // DoctorRepo'nun save metodu kullanılır
    }

    // Doktoru ID'ye göre getirmek için
    @Override
    public Doctor get(Long id) {
        // DoctorRepo'daki findById kullanılır, eğer bulunamazsa NotFound exception fırlatılır
        return this.doctorRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalı olarak doktorları getirmek için
    @Override
    public Page<Doctor> cursor(int page, int pageSize) {
        // Sayfalama yapmak için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.doctorRepo.findAll(pageable);// DoctorRepo'nun findAll metodu kullanılır
    }

    // Doktoru güncellemek için
    @Override
    public Doctor update(Doctor doctor) {
        this.get(doctor.getId());
        return this.doctorRepo.save(doctor);// DoctorRepo'nun save metodu kullanılır
    }

    // Doktoru silmek için
    @Override
    public boolean delete(long id) {
        Doctor doctor=this.get(id);
        this.doctorRepo.delete(doctor);// DoctorRepo'nun delete metodu ile silinir
        return true;// Silme işlemi başarılı olduğu için true döndürülür
    }
}
