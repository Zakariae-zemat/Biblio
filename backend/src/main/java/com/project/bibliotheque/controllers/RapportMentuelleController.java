package com.project.bibliotheque.controllers;

import com.project.bibliotheque.entities.RapportMentuelle;
import com.project.bibliotheque.services.RapportMentuelleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/rapportMentuelle")
public class RapportMentuelleController {
    private final RapportMentuelleService rapportMentuelleService;

    public RapportMentuelleController(RapportMentuelleService rapportMentuelleService) {
        this.rapportMentuelleService = rapportMentuelleService;
    }
    @GetMapping
    public List<RapportMentuelle> getRapportMentuelle(){
        return rapportMentuelleService.getAllRapportMentuelles();
    }
    @GetMapping("/{id}")
    public RapportMentuelle getRapportMentuelleById(Long id){
        return rapportMentuelleService.getRapportMentuelleById(id);
    }
    @PostMapping
    public RapportMentuelle addRapportMentuelle(RapportMentuelle rapportMentuelle){
        return rapportMentuelleService.addRapportMentuelle(rapportMentuelle);
    }
    @PutMapping("/{id}")
    public RapportMentuelle updateRapportMentuelle(Long id, RapportMentuelle rapportMentuelle){
        rapportMentuelle.setIdRapport(id);
        return rapportMentuelleService.updateRapportMentuelle(rapportMentuelle);
    }
    @DeleteMapping("/{id}")
    public void deleteRapportMentuelleById(Long id){
        rapportMentuelleService.deleteRapportMentuelleById(id);
    }
}
