package dev.patika.veterinersistemi.dto.request.Doctor;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
    @NotNull
    private String name;
    private Long phone;
    private String email;
    private String address;
    private String city;
}

