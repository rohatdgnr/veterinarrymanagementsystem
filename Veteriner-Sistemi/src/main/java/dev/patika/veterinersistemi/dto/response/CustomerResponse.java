package dev.patika.veterinersistemi.dto.response;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
