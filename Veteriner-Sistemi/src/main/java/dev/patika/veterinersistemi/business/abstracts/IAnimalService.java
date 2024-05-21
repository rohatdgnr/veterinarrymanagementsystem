package dev.patika.veterinersistemi.business.abstracts;


import dev.patika.veterinersistemi.entity.Animal;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IAnimalService {
    Animal save(Animal animal);
    Animal get(Long id);
    Page<Animal> cursor(int page, int pageSize);
    Animal update (Animal animal);
    boolean delete (long id);
    List<Animal> getAnimalsByName(String name);
    List<Animal> getAnimalsByCustomerId(Long customerId);
    Optional<Animal> getAnimalByName(String name);
}
