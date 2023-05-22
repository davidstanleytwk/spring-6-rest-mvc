package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CustomerService customerService;

    @Test
    void getCustomerById() throws Exception {

        Customer c = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Cust1")
                    .version("3")
                    .build();

        given(customerService.getCustomerById(c.getId())).willReturn(c);

        mockMvc.perform(
                            get("/api/v1/customer/"+c.getId().toString())
                            .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerName",is(c.getCustomerName())));
    }

    @Test
    void listCustomers() throws Exception {
        List<Customer> cList = List.of(
          Customer.builder()
                  .id(UUID.randomUUID())
                  .customerName("Customer1")
                  .build(),

                Customer.builder()
                        .id(UUID.randomUUID())
                        .customerName("Customer2")
                        .build(),
                Customer.builder()
                        .id(UUID.randomUUID())
                        .customerName("Customer3")
                        .build(),
                Customer.builder()
                        .id(UUID.randomUUID())
                        .customerName("Customer4")
                        .build(),
                Customer.builder()
                        .id(UUID.randomUUID())
                        .customerName("Customer5")
                        .build()
                );

        given(customerService.listCustomer()).willReturn(cList);

        mockMvc.perform(get("/api/v1/customer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(cList.size())))
                .andExpect(jsonPath("$.[0].customerName",is(cList.get(0).getCustomerName())));

    }

    @Test
    void createCustomer() throws Exception {
        Customer c= Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Testy McTesty")
                .build();

        c.setId(null);
        given( customerService.addCustomer(any(Customer.class))).willReturn(c);

        mockMvc.perform(
                        post("/api/v1/customer")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(c))
                    )

                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"));
    }
}