package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID,Customer> customerMap;
    public CustomerServiceImpl() {
        log.debug("Initialising customer data");
        Customer[] cArray = {
                Customer.builder()
                        .id(UUID.randomUUID())
                        .customerName("Fred Smith")
                        .version("1")
                        .lastUpdatedDate(LocalDateTime.now())
                        .createdDate(LocalDateTime.now())
                        .build(),
                Customer.builder()
                        .id(UUID.randomUUID())
                        .customerName("Jo Jones")
                        .version("1")
                        .lastUpdatedDate(LocalDateTime.now())
                        .createdDate(LocalDateTime.now())
                        .build(),
                Customer.builder()
                        .id(UUID.randomUUID())
                        .customerName("Arnold Dog")
                        .version("1")
                        .lastUpdatedDate(LocalDateTime.now())
                        .createdDate(LocalDateTime.now())
                        .build()
        };
        customerMap = new HashMap<>();

        Arrays.stream(cArray).forEach(c-> customerMap.put(c.getId(),c));
        log.debug("End customer service init");
    }

    @Override
    public Customer getCustomerById(UUID id) {
        log.debug("Service:: Get customer by id:"+id.toString());
        return customerMap.get(id);
    }
    @Override
    public List<Customer> listCustomer()
    {
        log.debug("Serice:: list customers");
        return new ArrayList<Customer>(customerMap.values());
    }

    @Override
    public Customer addCustomer(Customer c) {

        Customer newC= Customer.builder()
                .id(UUID.randomUUID())
                .version("1")
                .customerName(c.getCustomerName())
                .lastUpdatedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(newC.getId(),newC);
        return newC;
    }

    @Override
    public void updateCustomer(UUID id, Customer c) {
        Customer cu=customerMap.get(id);
        cu.setCustomerName(c.getCustomerName());
        cu.setVersion(c.getVersion());
        cu.setLastUpdatedDate(LocalDateTime.now());
    }

    @Override
    public void deleteCustomer(UUID cId) {
        log.debug("Customer Service::Deleting customer id:"+cId.toString());
        customerMap.remove(cId);
    }

    @Override
    public void patchCustomer(UUID id, Customer c) {

        Customer uc = customerMap.get(id);
        if( c.getCustomerName()!=null && !c.getCustomerName().isEmpty())
        {
            uc.setCustomerName(c.getCustomerName());
        }

        if( c.getVersion()!=null && !c.getVersion().isEmpty())
        {
            uc.setVersion(c.getVersion());
        }
        uc.setLastUpdatedDate(LocalDateTime.now());

    }
}
