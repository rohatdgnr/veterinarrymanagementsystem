package dev.patika.veterinersistemi.api;


import dev.patika.veterinersistemi.business.abstracts.IAnimalService;
import dev.patika.veterinersistemi.business.abstracts.IAppointmentService;
import dev.patika.veterinersistemi.business.abstracts.IAvailableDateService;
import dev.patika.veterinersistemi.business.abstracts.IDoctorService;
import dev.patika.veterinersistemi.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinersistemi.core.config.result.Result;
import dev.patika.veterinersistemi.core.config.result.ResultData;
import dev.patika.veterinersistemi.core.config.utiles.ResultHelper;
import dev.patika.veterinersistemi.dto.request.Appointment.AppointmentSaveRequest;
import dev.patika.veterinersistemi.dto.response.AppointmentResponse;
import dev.patika.veterinersistemi.entity.Animal;
import dev.patika.veterinersistemi.entity.Appointment;
import dev.patika.veterinersistemi.entity.Doctor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;
    private final IAnimalService animalService;
    private final IAvailableDateService availableDateService;


    // Endpoint that retrieves appointments by Doctor ID
    @GetMapping("/filter/doctor/{doctorId}")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDoctorId(@PathVariable("doctorId") long doctorId) {
        // Retrieves appointments associated with the given doctor ID
        List<Appointment> appointments = appointmentService.getByDoctorId(doctorId);
        // Maps the appointments to AppointmentResponse list
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.availableDateService.delete(id);
        return ResultHelper.Ok();
    }
    // Endpoint that retrieves appointments by Animal ID
    @GetMapping("/filter/animal/{animalId}")
    public ResultData<List<AppointmentResponse>> getAppointmentsByAnimalId(@PathVariable("animalId") long animalId) {
        // Retrieves appointments associated with the given animal ID
        List<Appointment> appointments = appointmentService.getByAnimalId(animalId);
        // Maps the appointments to AppointmentResponse list
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentResponse appointmentResponse ){
        Appointment updateAppointment = this.modelMapper.forRequest().map(appointmentResponse,Appointment.class);
        this.appointmentService.update(updateAppointment);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAppointment,AppointmentResponse.class));
    }

    // Endpoint that creates a new appointment record
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {

        Appointment saveAppointment = modelMapper.forResponse().map(appointmentSaveRequest, Appointment.class);

        // Doktorun müsait günlerini kontrol etme
        Doctor doctor = doctorService.get(appointmentSaveRequest.getDoctorId());
        if (!availableDateService.isDoctorAvailableOnDate(doctor.getId(), appointmentSaveRequest.getAppointmentDateTime().toLocalDate())) {
            return ResultHelper.errorWithData("Doktor müsait değil", null, HttpStatus.BAD_REQUEST);
        }
        if (appointmentService.isAppointmentConflict(doctor.getId(), appointmentSaveRequest.getAppointmentDateTime())) {
            return ResultHelper.errorWithData("Doktorun bu saatte başka bir randevusu var", null, HttpStatus.BAD_REQUEST);
        }

        saveAppointment.setDoctor(doctor);
        Animal animal = animalService.get(appointmentSaveRequest.getAnimalId());
        saveAppointment.setAnimal(animal);

        appointmentService.save(saveAppointment);
        return ResultHelper.created(modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class));
    }

    // Endpoint that retrieves an appointment by a specific appointment ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        // Retrieves the appointment associated with the given appointment ID
        Appointment appointment = this.appointmentService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));
    }

    // Endpoint that retrieves appointments within a specific date range
    @GetMapping("/filter/dateANDdoctor/appointments")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateRangeDoctor(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "doctorId", required = false) Long doctorId) {
        // Randevuları belirtilen tarih aralığında ve opsiyonel olarak doktor ID'sine göre al
        List<Appointment> appointments;
        if (doctorId != null) {
            appointments = appointmentService.getAppointmentsByDateRangeAndDoctorId(startDate, endDate, doctorId);
        } else {
            appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
        }

        // Eğer randevu bulunamadıysa
        if (appointments.isEmpty()) {
            return ResultHelper.errorWithData("Belirtilen tarih aralığında randevu bulunamadı.", null, HttpStatus.NOT_FOUND);
        }

        // Randevuları AppointmentResponse listesine dönüştür
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(appointmentResponses);
    }
    @GetMapping("/filter/dateANDanimal")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateRangeAnimal(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "animalId", required = false) Long animalId) {

        List<Appointment> appointments;

        if (animalId != null) {
            appointments = appointmentService.getAppointmentsByDateRangeAndAnimalId(startDate, endDate, animalId);
        } else {
            appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
        }

        if (appointments.isEmpty()) {
            return ResultHelper.errorWithData("Belirtilen tarih aralığında randevu bulunamadı.", null, HttpStatus.NOT_FOUND);
        }

        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(appointmentResponses);
    }
    @GetMapping("/filterAnimalName/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getAnimalsByCustomerName(@PathVariable("animalName") String animalName) {
        Optional<Animal> animalOptional = this.animalService.getAnimalByName(animalName);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            // Retrieves appointments associated with the given animal ID
            List<Appointment> appointments = appointmentService.getByAnimalId(animal.getId());
            // Maps the appointments to AppointmentResponse list
            List<AppointmentResponse> appointmentResponses = appointments.stream()
                    .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                    .collect(Collectors.toList());

            return ResultHelper.success(appointmentResponses);

        } else {
            return ResultHelper.errorWithData("Kullanıcı bulunamadı.", null, HttpStatus.NOT_FOUND);
        }
    }


}
