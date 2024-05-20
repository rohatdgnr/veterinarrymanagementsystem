package dev.patika.veterinersistemi.business.concretes;

import dev.patika.veterinersistemi.business.abstracts.ICustomerService;
import dev.patika.veterinersistemi.core.config.exception.NotFoundException;
import dev.patika.veterinersistemi.core.utiles.Msg;
import dev.patika.veterinersistemi.dao.CustomerRepository;
import dev.patika.veterinersistemi.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    // CustomerRepo bağımlılığını enjekte etmek için constructor
    private final CustomerRepository customerRepo;

    // Constructor enjeksiyonu
    public CustomerService(CustomerRepository customerRepo){
        this.customerRepo = customerRepo;
    }

    // Yeni bir müşteri kaydetmek için
    @Override
    public Customer save(Customer customer) {
        return this.customerRepo.save(customer);// CustomerRepo'nun save metodu kullanılır
    }

    // Müşteriyi ID'ye göre getirmek için
    @Override
    public Customer get(long id) {
        // CustomerRepo'daki findById kullanılır, eğer bulunamazsa NotFound exception fırlatılır
        return customerRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalı olarak müşterileri getirmek için
    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        // Sayfalama için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.customerRepo.findAll(pageable);// CustomerRepo'nun findAll metodu kullanılır
    }

    // Müşteriyi güncellemek için
    @Override
    public Customer update(Customer customer) {
        this.get(customer.getId());
        return this.customerRepo.save(customer);
    }

    // Müşteri silmek için
    @Override
    public boolean delete(long id) {
        Customer customer = this.get(id);// ID'ye göre müşteri getirilir
        this.customerRepo.delete(customer);// CustomerRepo'nun delete metodu ile silinir
        return true; // Silme işlemi başarılı olduğu için true döndürülür
    }

    // İsim içeren müşterileri getirmek için
    @Override
    public List<Customer> getCustomersByName(String name) {
        return customerRepo.findByNameContainingIgnoreCase(name);// İsim içeren müşterileri getirir
    }
}