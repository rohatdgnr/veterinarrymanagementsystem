package dev.patika.veterinersistemi.api;



import dev.patika.veterinersistemi.business.abstracts.ICustomerService;
import dev.patika.veterinersistemi.core.config.EmailAlreadyRegisteredException;
import dev.patika.veterinersistemi.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinersistemi.core.config.result.Result;
import dev.patika.veterinersistemi.core.config.result.ResultData;
import dev.patika.veterinersistemi.core.config.utiles.ResultHelper;
import dev.patika.veterinersistemi.dto.CursorResponse;
import dev.patika.veterinersistemi.dto.request.Customer.CustomerSaveRequest;
import dev.patika.veterinersistemi.dto.request.Customer.CustomerUpdateRequest;
import dev.patika.veterinersistemi.dto.response.CustomerResponse;
import dev.patika.veterinersistemi.entity.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
    public class CustomerController {
        private final ICustomerService customerService;
        private final IModelMapperService modelMapper;

    // Yeni bir müşteri kaydetmek için POST endpoint'i
    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest ){
        if (customerService.existsByMail(customerSaveRequest.getMail())) {
            throw new EmailAlreadyRegisteredException("Email is already registered");
        }
        Customer saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest,Customer.class);
        this.customerService.save(saveCustomer);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveCustomer,CustomerResponse.class));
    }


    // Müşteri detaylarını almak için GET endpoint'i
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get (@PathVariable("id") long id) {
        Customer customer = this.customerService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(customer,CustomerResponse.class));
    }

    // Müşteri bilgilerini güncellemek için PUT endpoint'i
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest ){
        Customer updateCustomer = this.modelMapper.forRequest().map(customerUpdateRequest,Customer.class);
        this.customerService.update(updateCustomer);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateCustomer,CustomerResponse.class));
    }
    // Müşteriyi silmek için DELETE endpoint'i
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        this.customerService.delete(id);
        return ResultHelper.Ok();
    }

    // Sayfalı olarak müşterileri listelemek için GET endpoint'i
    @GetMapping("/customersList")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page", required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false,defaultValue = "5") int pageSize
    ){
        // Sayfalama için ilgili servis metodu çağrılıyor
        Page<Customer> categoryPage = this.customerService.cursor(page,pageSize);
        Page<CustomerResponse> customerResponsePage = categoryPage
                .map(category -> this.modelMapper.forResponse().map(category,CustomerResponse.class));
        // Sonuç dönüşü yapılıyor
        return  ResultHelper.cursor(customerResponsePage);
    }
    //müşterileri isme göre filtreleyen bir endpoint
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> getCustomersByName(@RequestParam("name") String name) {
        List<Customer> customers = this.customerService.getCustomersByName(name);

        // Bu isme göre filtrelenmiş müşterileri CustomerResponse formatına dönüştürüyoruz
        List<CustomerResponse> customerResponses = customers.stream()
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(customerResponses);
    }

}
