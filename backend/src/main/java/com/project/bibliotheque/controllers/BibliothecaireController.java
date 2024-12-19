package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.BibliothecaireDto;
import com.project.bibliotheque.entities.Bibliothecaire;
import com.project.bibliotheque.entities.Client;
import com.project.bibliotheque.entities.Utilisateur;
import com.project.bibliotheque.repositories.BibliothecaireRipository;
import com.project.bibliotheque.repositories.UtilisateurRepository;
import com.project.bibliotheque.services.BibliothecaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/bibliothecaire")
public class BibliothecaireController{
    private final UtilisateurRepository utilisateurRepository;
    private final BibliothecaireRipository bibliothecaireRipository;
    private final BibliothecaireService bibliothecaireService;

    public BibliothecaireController(BibliothecaireService bibliothecaireService, BibliothecaireRipository bibliothecaireRipository,UtilisateurRepository utilisateurRepository) {
        this.bibliothecaireService = bibliothecaireService;
        this.bibliothecaireRipository = bibliothecaireRipository;
        this.utilisateurRepository = utilisateurRepository;
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public List<BibliothecaireDto> getAllBibliothecaire(){
        return bibliothecaireService.getAllBibliothecaires();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public BibliothecaireDto getBibliothecaireById(@PathVariable Long id){
        return bibliothecaireService.getBibliothecaireById(id);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Map<String,String>> addBibliothecaire(@RequestBody Map<Object,Object> body){
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String nom = (String) body.get("nom");
        String prenom =  (String) body.get("prenom");
        String telephone = (String) body.get("telephone");
        Object naissanceObj = body.get("naissance");
        Date naissance = null;
        if (naissanceObj instanceof Date) {
            naissance = (Date) naissanceObj;
        } else if (naissanceObj instanceof String) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                naissance = formatter.parse((String) naissanceObj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String imageUrl = (String) body.get("imageUrl");
        Utilisateur existingUser = utilisateurRepository.findByEmail(email);
        if (existingUser != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is already in use"));
        }

        Bibliothecaire newUser = new Bibliothecaire();
        newUser.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        newUser.setNom(nom);
        newUser.setPrenom(prenom);
        newUser.setTelephone(telephone);
        newUser.setImageUrl(imageUrl);
        newUser.setNaissance(naissance);
        newUser.setRole("BIBLIOTHECAIRE");
        newUser.setStatus("active");
        bibliothecaireRipository.save(newUser);
        return ResponseEntity.ok(Map.of("message", "Bibliothecaire added successfully"));
    }
    @PreAuthorize("hasRole('bibliothequaire') or hasRole('admin')")
    @PutMapping("/{id}")
    public BibliothecaireDto updateBibliothecaire(@PathVariable Long id, @RequestBody BibliothecaireDto bibliothecaireDto){
        bibliothecaireDto.setId(id);
        return bibliothecaireService.updateBibliothecaire(bibliothecaireDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping
    public void deleteBibliothecaireById(@RequestParam Long id){
        bibliothecaireService.deleteBibliothecaireById(id);
    }
}