package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.CarteClientDto;
import com.project.bibliotheque.services.CarteClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/carteClient")
public class CarteClientController {
    private final CarteClientService carteClientService;

    public CarteClientController(CarteClientService carteClientService) {
        this.carteClientService = carteClientService;
    }
    @GetMapping
    public List<CarteClientDto> getCarteClient(){
        return carteClientService.getAllCarteClients();
    }
    @GetMapping("/client")
    public CarteClientDto getCarteClientById(@RequestParam Long id){
        return carteClientService.getCarteClientById(id);
    }
    @PostMapping
    public CarteClientDto addCarteClient(@RequestParam String email){
        return carteClientService.addCarteClient(email);
    }
    @GetMapping("/carte")
    public CarteClientDto getCarteClientByCarte(@RequestParam String email){
        return carteClientService.addCarteClient(email);
    }
    @PutMapping("/{id}")
    public CarteClientDto updateCarteClient(Long id, CarteClientDto carteClientDto){
        carteClientDto.setIdCarteClient(id);
        return carteClientService.updateCarteClient(carteClientDto);
    }
    @DeleteMapping("/{id}")
    public void deleteCarteClientById(Long id){
        carteClientService.deleteCarteClientById(id);
    }
}