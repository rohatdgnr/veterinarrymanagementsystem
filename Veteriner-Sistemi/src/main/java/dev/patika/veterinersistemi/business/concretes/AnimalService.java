package dev.patika.veterinersistemi.business.concretes;


import dev.patika.veterinersistemi.business.abstracts.IAnimalService;
import dev.patika.veterinersistemi.core.config.exeption.NotFoundException;
import dev.patika.veterinersistemi.core.config.utiles.Msg;
import dev.patika.veterinersistemi.dao.AnimalRepository;
import dev.patika.veterinersistemi.entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService implements IAnimalService {
    // AnimalRepo bağımlılığını enjekte etmek için constructor
    private final AnimalRepository animalRepo;

    // Constructor enjeksiyonu

    // Yeni bir hayvan kaydetmek için
    @Override
    public Animal save(Animal animal) {
        return animalRepo.save(animal);// AnimalRepo'nun save metodu kullanılır
    }

    // Hayvanı ID'ye göre getirmek için
    @Override
    public Animal get(Long id) {
        // AnimalRepo'daki findById kullanılır, eğer bulunamazsa NotFound exception fırlatılır
        return this.animalRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalı olarak hayvanları getirmek için
    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        // Sayfalama için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.animalRepo.findAll(pageable);// AnimalRepo'nun findAll metodu kullanılır
    }

    // Hayvanı güncellemek için
    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal);// AnimalRepo'nun save metodu kullanılır
    }

    // Hayvanı silmek için
    @Override
    public boolean delete(long id) {// ID'ye göre hayvan getirilir
        Animal animal =this.get(id); // AnimalRepo'nun delete metodu ile silinir
        this.animalRepo.delete(animal);// Silme işlemi başarılı olduğu için true döndürülür
        return true;
    }

    @Override
    public List<Animal> getAnimalsByName(String name) {
        return this.animalRepo.findByNameIgnoreCaseContaining(name);
    }
    @Override
    public List<Animal> getAnimalsByCustomerId(Long customerId) {
        return this.animalRepo.findByCustomerId(customerId);
    }

    @Override
    public Optional<Animal> getAnimalByName(String name) {
            return animalRepo.findByName(name);
        }
    }


