package dev.patika.veterinersistemi.dto.request.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSaveRequest {

    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}