package com.project.bibliotheque.controllers;


import com.project.bibliotheque.entities.RapportQuotidienne;
import com.project.bibliotheque.services.RapportQuotidienneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/rapportQuotidienne")
public class RapportQuotidienneController {
    private final RapportQuotidienneService rapportQuotidienneService;

    public RapportQuotidienneController(RapportQuotidienneService rapportQuotidienneService) {
        this.rapportQuotidienneService = rapportQuotidienneService;
    }
    @GetMapping
    public List<RapportQuotidienne> getRapportQuotidienne(){
        return rapportQuotidienneService.getAllRapportQuotidiennes();
    }
    @GetMapping("/{id}")
    public RapportQuotidienne getRapportQuotidienneById(Long id){
        return rapportQuotidienneService.getRapportQuotidienneById(id);
    }
    @PostMapping
    public RapportQuotidienne addRapportQuotidienne(RapportQuotidienne rapportQuotidienne){
        return rapportQuotidienneService.addRapportQuotidienne(rapportQuotidienne);
    }
    @PutMapping("/{id}")
    public RapportQuotidienne updateRapportQuotidienne(Long id, RapportQuotidienne rapportQuotidienne){
        rapportQuotidienne.setIdRapport(id);
        return rapportQuotidienneService.updateRapportQuotidienne(rapportQuotidienne);
    }
    @DeleteMapping("/{id}")
    public void deleteRapportQuotidienneById(Long id){
        rapportQuotidienneService.deleteRapportQuotidienneById(id);
    }
}
