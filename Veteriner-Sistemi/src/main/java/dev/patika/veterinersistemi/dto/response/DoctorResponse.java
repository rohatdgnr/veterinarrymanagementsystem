package dev.patika.veterinersistemi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
