package dev.patika.veterinersistemi.api;


import dev.patika.veterinersistemi.business.abstracts.IAnimalService;
import dev.patika.veterinersistemi.business.abstracts.ICustomerService;
import dev.patika.veterinersistemi.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinersistemi.core.config.result.Result;
import dev.patika.veterinersistemi.core.config.result.ResultData;
import dev.patika.veterinersistemi.core.config.utiles.ResultHelper;
import dev.patika.veterinersistemi.dto.request.Animal.AnimalSaveRequest;
import dev.patika.veterinersistemi.dto.request.Animal.AnimalUpdateRequest;
import dev.patika.veterinersistemi.dto.response.AnimalResponse;
import dev.patika.veterinersistemi.dto.response.VaccineResponse;
import dev.patika.veterinersistemi.entity.Animal;
import dev.patika.veterinersistemi.entity.Customer;
import dev.patika.veterinersistemi.entity.Vaccine;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final IAnimalService animalService;
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;

    // Constructor-based dependency injection

    // Belirli bir hayvanın detaylarını getiren endpoint
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get (@PathVariable("id") Long id) {
        Animal animal = this.animalService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(animal,AnimalResponse.class));
    }

    // Yeni hayvan ekleme endpoint'i
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest ){
        Animal saveAnimal = this.modelMapper.forRequest().map(animalSaveRequest,Animal.class);

        Customer customer =this.customerService.get(animalSaveRequest.getCustomerId());
        saveAnimal.setCustomer(customer);

        this.animalService.save(saveAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal,AnimalResponse.class));
    }

    // Hayvan güncelleme endpoint'i
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest ){
        Animal updateAnimal = this.modelMapper.forRequest().map(animalUpdateRequest,Animal.class);
        this.animalService.update(updateAnimal);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAnimal,AnimalResponse.class));
    }

    // Hayvan silme endpoint'i
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.animalService.delete(id);
        return ResultHelper.Ok();
    }
    // Hayvana ait aşıları getiren endpoint
    @GetMapping("/{id}/vaccines")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesForAnimal(@PathVariable("id") Long id) {
        Animal animal = this.animalService.get(id);

        // Burada, animal.getVaccines() şeklinde hayvana ait tüm aşı kayıtlarını alabilirsiniz
        List<Vaccine> vaccines = animal.getVaccines();

        // Bu aşı kayıtlarını VaccineResponse'a dönüştürerek döndürebilirsiniz
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(vaccineResponses);
    }
    @GetMapping("/filterAnimalName") //http://localhost:8047/v1/animals/filter?name=Şila
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByName(@RequestParam("name") String name) {
        List<Animal> animals = this.animalService.getAnimalsByName(name);

        // İsmine göre filtrelenmiş hayvanları AnimalResponse formatına dönüştürüyoruz
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(animalResponses);
    }
    @GetMapping("/customer/filterId{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<Animal> animals = this.animalService.getAnimalsByCustomerId(customerId);

        // Sahibinin hayvanlarını AnimalResponse formatına dönüştürüyoruz
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(animalResponses);
    }
    @GetMapping("/customer/filterCustomerName/{customerName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByCustomerName(@PathVariable("customerName") String customerName) {
        Optional<Customer> customerOptional = this.customerService.getCustomerByName(customerName);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            List<Animal> animals = this.animalService.getAnimalsByCustomerId(customer.getId());

            List<AnimalResponse> animalResponses = animals.stream()
                    .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                    .collect(Collectors.toList());

            return ResultHelper.success(animalResponses);
        } else {
            return ResultHelper.errorWithData("Kullanıcı bulunamadı.", null, HttpStatus.NOT_FOUND);
        }
    }
}
