package com.project.bibliotheque.controllers;


import com.project.bibliotheque.dtos.AlocationDto;
import com.project.bibliotheque.entities.CarteClient;
import com.project.bibliotheque.entities.Document;
import com.project.bibliotheque.entities.Location;
import com.project.bibliotheque.entities.Pret;
import com.project.bibliotheque.repositories.CartClientRepository;
import com.project.bibliotheque.repositories.DocumentRepository;
import com.project.bibliotheque.services.LocationService;
import com.project.bibliotheque.services.RapportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;
    private final CartClientRepository cartClientRepository;
    private final DocumentRepository documentRepository;
    private final RapportService rapportService;
    public LocationController(LocationService locationService,DocumentRepository documentRepository,CartClientRepository cartClientRepository,RapportService rapportService){
        this.locationService = locationService;
        this.cartClientRepository = cartClientRepository;
        this.documentRepository = documentRepository;
        this.rapportService = rapportService;
    }
    @GetMapping
    public List<AlocationDto> getAllLocations(){
        return locationService.getAllLocations();
    }
    @GetMapping("/{id}")
    public Location getLocationById(Long id){
        return locationService.getLocationById(id);
    }

    @PutMapping("/{id}")
    public Location updateLocation(Long id, Location location){
        location.setIdTransaction(id);
        return locationService.updateLocation(location);
    }
    @DeleteMapping
    public void deleteLocationById(@RequestParam Long id){
        //getPretById(id);
        Location location= locationService.getLocationById(id);
        Document document = location.getDocument();
        CarteClient carteClient = location.getCarteClient();
        carteClient.setNbrEmprunte(carteClient.getNbrEmprunte() - 1);
        rapportService.addRapport(new java.util.Date(), "retour", document.getIdDocument());
        cartClientRepository.save(carteClient);
        documentRepository.save(document);
        document.setNombreExemplaire(document.getNombreExemplaire() + 1);
        locationService.deleteLocationById(id);
    }
}
