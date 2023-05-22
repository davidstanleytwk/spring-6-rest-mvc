package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(value="{beerId}", method=RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId")  UUID beerId)
    {
        log.debug("Controller is getting beer by id: "+beerId.toString());

        return beerService.getBeerById(beerId);
    }

    @RequestMapping(method= RequestMethod.GET)
    public List<Beer> listBeers()
    {
        return beerService.listBeers();
    }

    @PostMapping
    public ResponseEntity addBeer(@RequestBody Beer b)
    {
        Beer newBeer = beerService.addBeer(b);
        HttpHeaders h = new HttpHeaders();
        h.add("Location","/api/v1/beer/"+newBeer.getId().toString());

        return new ResponseEntity(h,HttpStatus.CREATED);
    }

    @PutMapping("{beerId}")
    public ResponseEntity updateBeer( @PathVariable("beerId") UUID id, @RequestBody Beer b)
    {
        beerService.updateBeer(id, b);


        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{beerId}")
    public ResponseEntity patchBeer( @PathVariable("beerId") UUID id, @RequestBody Beer b)
    {
        beerService.patchBeer(id, b);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteBeer(@PathVariable("beerId") UUID beerId)
    {
        beerService.deleteBeer(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
