package dev.patika.veterinersistemi.api;

import dev.patika.veterinersistemi.business.abstracts.IAppointmentService;
import dev.patika.veterinersistemi.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinersistemi.core.result.Result;
import dev.patika.veterinersistemi.core.result.ResultData;
import dev.patika.veterinersistemi.core.utiles.ResultHelper;
import dev.patika.veterinersistemi.dto.request.Appointment.AppointmentSaveRequest;
import dev.patika.veterinersistemi.dto.response.AppointmentResponse;
import dev.patika.veterinersistemi.dto.response.AvailableDataResponse;
import dev.patika.veterinersistemi.entity.Appointment;
import dev.patika.veterinersistemi.entity.AvailableDate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;

    public AppointmentController(IAppointmentService appointmentService, IModelMapperService modelMapper) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

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

    // Endpoint that creates a new appointment record
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest ){
        // Converts AppointmentSaveRequest to Appointment class and saves it
        Appointment saveAppointment = this.modelMapper.forRequest().map(appointmentSaveRequest,Appointment.class);
        this.appointmentService.save(saveAppointment);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAppointment,AppointmentResponse.class));
    }

    // Endpoint that retrieves an appointment by a specific appointment ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get (@PathVariable("id") Long id) {
        // Retrieves the appointment associated with the given appointment ID
        Appointment appointment = this.appointmentService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(appointment,AppointmentResponse.class));
    }

    // Endpoint that retrieves appointments within a specific date range
    @GetMapping("/filter/date")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        // Retrieves appointments between startDate and endDate parameters
        List<Appointment> appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
        // Maps the appointments to AppointmentResponse list
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.appointmentService.delete(id);
        return ResultHelper.Ok();
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentResponse appointmentResponse ){
        Appointment updateAppointment = this.modelMapper.forRequest().map(appointmentResponse,Appointment.class);
        this.appointmentService.update(updateAppointment);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAppointment,AppointmentResponse.class));
    }

}