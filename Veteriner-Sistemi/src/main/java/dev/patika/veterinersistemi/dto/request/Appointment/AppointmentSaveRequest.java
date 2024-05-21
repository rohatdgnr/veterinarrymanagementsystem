package dev.patika.veterinersistemi.dto.request.Appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest {
   @JsonFormat(pattern = "yyyy-MM-dd'T'HH")
    private LocalDateTime appointmentDateTime;
    private long doctorId;
    private long animalId;
}
