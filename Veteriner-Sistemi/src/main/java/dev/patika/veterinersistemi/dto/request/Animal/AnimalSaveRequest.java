package dev.patika.veterinersistemi.dto.request.Animal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalSaveRequest {

    private String name;
    private String species;
    private String breed;
    @Pattern
    (regexp = "erkek|dişi", message = "Gender must be either 'erkek' or 'dişi'")
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
    private long customerId;

}

