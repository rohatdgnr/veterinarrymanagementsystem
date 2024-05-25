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

    private final AnimalRepository animalRepo;


    @Override
    public Animal save(Animal animal) {
        return animalRepo.save(animal);
    }

    @Override
    public Animal get(Long id) {

        return this.animalRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }


    @Override
    public Page<Animal> cursor(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page,pageSize);
        return this.animalRepo.findAll(pageable);
    }


    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal);
    }


    @Override
    public boolean delete(long id) {
        Animal animal =this.get(id);
        this.animalRepo.delete(animal);
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


