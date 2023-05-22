package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer getCustomerById(UUID id);

    List<Customer> listCustomer();

    Customer addCustomer(Customer c);

    void updateCustomer(UUID id, Customer c);

    void deleteCustomer(UUID cId);

    void patchCustomer(UUID id, Customer c);
}
