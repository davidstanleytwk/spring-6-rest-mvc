package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerService;
import guru.springframework.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    //@Autowired
    //BeerController beerController;

    @Autowired
    MockMvc  mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl=new BeerServiceImpl();
    @Test
    void getBeerById() throws Exception {

        Beer testBeer = beerServiceImpl.listBeers().get(0);

        // configure the mock bean to return a beer given an ID
        given(beerService.getBeerById(testBeer.getId()))
                .willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/"+testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(testBeer.getBeerName())));


    }

    @Test
    void listBeers() throws Exception {
        List<Beer> beerList = List.of(
                Beer.builder()
                    .id(UUID.randomUUID())
                    .beerName("Beer1")
                    .build(),
                Beer.builder()
                        .id(UUID.randomUUID())
                        .beerName("Beer2")
                        .build(),
                Beer.builder()
                        .id(UUID.randomUUID())
                        .beerName("Beer3")
                        .build(),
                Beer.builder()
                        .id(UUID.randomUUID())
                        .beerName("Beer4")
                        .build()
        );
        given(beerService.listBeers()).willReturn(beerList);

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[1].beerName",is("Beer2")))
                .andExpect(jsonPath("$.length()", is(beerList.size())));
    }

    @Test
    void createBeer() throws Exception {

        Beer b = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("London Pride")
                .beerStyle(BeerStyle.ALE)
                .price(new BigDecimal("4.30"))
                .quantityOnHand(333)
                .build();

        given(beerService.addBeer(any(Beer.class))).willReturn(b);

        mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(b)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));


    }
}