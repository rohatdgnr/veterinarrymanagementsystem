package dev.patika.veterinersistemi.dto.request.Customer;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class CustomerUpdateRequest {
    @Positive(message = "ID Değeri pozitif olmak zorunda")
    private long id;
    @NotNull(message = "Müşteri ismi boş veya null olamaz")
    private String name;
    @NotBlank(message = "Telefon numarası sayısal bir değer olmalıdır")
    private Long phone;
    @Email
    private String mail;
    private String address;
    private String city;


    }

