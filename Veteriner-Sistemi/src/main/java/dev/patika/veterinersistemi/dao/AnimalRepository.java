package dev.patika.veterinersistemi.dao;


import dev.patika.veterinersistemi.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal,Long> {
    List<Animal> findByNameIgnoreCaseContaining(String name);
   List<Animal> findByCustomerId(Long customerId);
    Optional<Animal> findByName(String name);

}