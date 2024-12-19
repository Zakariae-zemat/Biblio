package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.BibliothequeDto;
import com.project.bibliotheque.services.BibliothequeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/bibliotheque")
public class BibliothequeController{
    private final BibliothequeService bibliothequeService;

    public BibliothequeController(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }
    @GetMapping
    public List<BibliothequeDto> getAllBibliotheque(){
        return bibliothequeService.getAllBibliotheques();
    }
    @GetMapping("/{id}")
    public BibliothequeDto getBibliothequeById(@PathVariable Long id){
        return bibliothequeService.getBibliothequeById(id);
    }
    @PostMapping
    public BibliothequeDto addBibliotheque(@RequestBody BibliothequeDto bibliothequeDto){
        return bibliothequeService.addBibliotheque(bibliothequeDto);
    }
    @PutMapping("/{id}")
    public BibliothequeDto updateBibliotheque(@PathVariable Long id, @RequestBody BibliothequeDto bibliothequeDto){
        bibliothequeDto.setId(id);
        return bibliothequeService.updateBibliotheque(bibliothequeDto);
    }
    @DeleteMapping("/{id}")
    public void deleteBibliothequeById(@PathVariable Long id){
        bibliothequeService.deleteBibliothequeById(id);
    }
}