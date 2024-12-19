package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.DiscripteurDto;
import com.project.bibliotheque.services.DiscripteurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/discripteurs")
public class DiscripteurController {
    private final DiscripteurService discripteurService;

    public DiscripteurController(DiscripteurService discripteurService) {
        this.discripteurService = discripteurService;
    }

    @GetMapping
    public List<DiscripteurDto> getAllDiscripteurs(){
        return discripteurService.getAllDiscripteur();
    }

    @GetMapping("/{id}")
    public DiscripteurDto getDiscripteurById(@PathVariable Long id){
        return discripteurService.getDiscripteurById(id);
    }

    @PostMapping
    public DiscripteurDto addDiscripteur(@RequestBody DiscripteurDto discripteurDto){
        return discripteurService.addDiscripteur(discripteurDto);
    }

    @PutMapping("/{id}")
    public DiscripteurDto updateDiscripteur(@PathVariable Long id, @RequestBody DiscripteurDto discripteurDto){
        discripteurDto.setId(id);
        return discripteurService.updateDiscripteur(discripteurDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDiscripteurById(@PathVariable Long id){
        discripteurService.deleteDiscripteurById(id);
    }
}