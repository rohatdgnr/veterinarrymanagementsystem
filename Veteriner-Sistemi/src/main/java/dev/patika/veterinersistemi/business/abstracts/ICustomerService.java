package dev.patika.veterinersistemi.business.abstracts;

import dev.patika.veterinersistemi.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    Customer save(Customer customer);
    Customer get(long id);
    Page<Customer> cursor(int page, int pageSize);
    Customer update (Customer customer);
    boolean delete (long id);
    List<Customer> getCustomersByName(String name);
}
