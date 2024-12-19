package com.project.bibliotheque.controllers;


import com.project.bibliotheque.entities.RapportAnnuelle;
import com.project.bibliotheque.services.RapportAnnuelleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/rapportAnnuelle")
public class RapportAnnuelleController {
    private final RapportAnnuelleService rapportAnnuelleService;

    public RapportAnnuelleController(RapportAnnuelleService rapportAnnuelleService) {
        this.rapportAnnuelleService = rapportAnnuelleService;
    }

    @GetMapping
    public List<RapportAnnuelle> getRapportAnnuelle(){
        return rapportAnnuelleService.getAllRapportAnnuelles();
    }
    @GetMapping("/{id}")
    public RapportAnnuelle getRapportAnnuelleById(Long id){
        return rapportAnnuelleService.getRapportAnnuelleById(id);
    }
    @PostMapping
    public RapportAnnuelle addRapportAnnuelle(RapportAnnuelle rapportAnnuelle){
        return rapportAnnuelleService.addRapportAnnuelle(rapportAnnuelle);
    }
    @PutMapping("/{id}")
    public RapportAnnuelle updateRapportAnnuelle(Long id, RapportAnnuelle rapportAnnuelle){
        rapportAnnuelle.setIdRapport(id);
        return rapportAnnuelleService.updateRapportAnnuelle(rapportAnnuelle);
    }
    @DeleteMapping("/{id}")
    public void deleteRapportAnnuelleById(Long id){
        rapportAnnuelleService.deleteRapportAnnuelleById(id);
    }
}
