package dev.patika.veterinersistemi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDataResponse {
    private LocalDate availableDate;
    private long doctorId;
}
