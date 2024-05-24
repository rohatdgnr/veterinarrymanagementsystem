package dev.patika.veterinersistemi.api;


import dev.patika.veterinersistemi.business.abstracts.IAvailableDateService;
import dev.patika.veterinersistemi.business.abstracts.IDoctorService;
import dev.patika.veterinersistemi.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinersistemi.core.config.result.Result;
import dev.patika.veterinersistemi.core.config.result.ResultData;
import dev.patika.veterinersistemi.core.config.utiles.ResultHelper;
import dev.patika.veterinersistemi.dto.request.Available.AvailableDataUpdateRequest;
import dev.patika.veterinersistemi.dto.request.Available.AvailableDateSaveRequest;
import dev.patika.veterinersistemi.dto.response.AvailableDataResponse;
import dev.patika.veterinersistemi.entity.AvailableDate;
import dev.patika.veterinersistemi.entity.Doctor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/available-dates")
@RequiredArgsConstructor
public class AvailableDateController {
    private  final IAvailableDateService availableDateService;
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;



    //Mevcut verileri belirli bir kimliğe göre alan end point
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDataResponse> get (@PathVariable("id") Long id) {
        AvailableDate availableDate = this.availableDateService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(availableDate,AvailableDataResponse.class));
    }
    // Yeni bir kullanılabilir veri kaydı oluşturan  end point
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDataResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest ){
        // Maps the AvailableDateSaveRequest to AvailableDate class and saves it
        AvailableDate saveAvailableDate = this.modelMapper.forRequest().map(availableDateSaveRequest,AvailableDate.class);
        // Retrieves the doctor associated with the request and sets it in the available date
        Doctor doctor =this.doctorService.get(availableDateSaveRequest.getDoctorId());
        saveAvailableDate.setDoctor(doctor);

        this.availableDateService.save(saveAvailableDate);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAvailableDate,AvailableDataResponse.class));
    }
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDataResponse> update(@PathVariable Long id, @Valid @RequestBody AvailableDataUpdateRequest availableDataUpdateRequest) {
        AvailableDate updateAvailableDate = this.modelMapper.forRequest().map(availableDataUpdateRequest, AvailableDate.class);

        // Yeni doktor kimliğini al ve AvailableDate'in doktor bilgilerini güncelle
        Doctor doctor = this.doctorService.get(availableDataUpdateRequest.getDoctorId());
        updateAvailableDate.setDoctor(doctor);

        // AvailableDate'in ID'sini ayarla
        updateAvailableDate.setId(id);

        this.availableDateService.update(updateAvailableDate);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAvailableDate, AvailableDataResponse.class));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {this.availableDateService.delete(id);return ResultHelper.Ok();
    }
}