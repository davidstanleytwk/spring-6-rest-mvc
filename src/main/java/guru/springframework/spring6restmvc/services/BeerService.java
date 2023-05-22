package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);

    List<Beer> listBeers();

    Beer addBeer(Beer b);

    void updateBeer(UUID id, Beer b);

    void deleteBeer(UUID beerId);

    void patchBeer(UUID id, Beer b);
}
