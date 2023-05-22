package guru.springframework.spring6restmvc.controller;


import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(value="{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId)
    {
        log.debug("Controller: Getting cusotmer id:"+customerId.toString());
        return customerService.getCustomerById(customerId);
    }

    @RequestMapping( method = RequestMethod.GET)
    public List<Customer> listCustomers()
    {
        return customerService.listCustomer();
    }

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody Customer c)
    {
        Customer newCustomer = customerService.addCustomer(c);

        HttpHeaders h = new HttpHeaders();
        h.add("Location", "/api/v1/customer/"+newCustomer.getId().toString());

        return new ResponseEntity(h, HttpStatus.CREATED);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer c)
    {
        customerService.updateCustomer(id, c);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable("customerId") UUID cId)
    {
        customerService.deleteCustomer(cId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("{customerId}")
    public ResponseEntity patchCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer c)
    {
        customerService.patchCustomer(id,c);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
