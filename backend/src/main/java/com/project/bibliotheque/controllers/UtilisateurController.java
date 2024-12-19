package com.project.bibliotheque.controllers;


import com.project.bibliotheque.dtos.UtilisateurDto;
import com.project.bibliotheque.entities.Utilisateur;
import com.project.bibliotheque.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/users")
public class UtilisateurController {
    UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    @GetMapping("/all")
    public List<UtilisateurDto> getAllUtilisateurs(){
        return utilisateurService.getAllUtilisateurs();
    }
    @GetMapping("/user")
    public ResponseEntity<Map<String,UtilisateurDto>> getUtilisateurByEmail(@RequestParam String email){
        try{
            UtilisateurDto utilisateur = utilisateurService.getUtilisateurByEmail(email);
            return ResponseEntity.ok().body(Map.of("utilisateur",utilisateur));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("Utilisateur non trouve",null));
        }
    }
    @PutMapping
    public ResponseEntity<Map<String,UtilisateurDto>> updateUtilisateur(@RequestParam String email,@RequestBody Map<String,String> utilisateur) {
        String nom = utilisateur.get("nom");
        String prenom = utilisateur.get("prenom");
        String adresse = utilisateur.get("adresse");
        String telephone = utilisateur.get("telephone");
        String cin = utilisateur.get("cin");
        String url = utilisateur.get("imageUrl");
        Object naissanceObj = utilisateur.get("naissance");
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
            try{
            UtilisateurDto utilisateurDto = utilisateurService.updateUtilisateur(email,nom,prenom,adresse,telephone,naissance,url,cin);
            return ResponseEntity.ok().body(Map.of("utilisateur",utilisateurDto));
            }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("Utilisateur non trouve",null));
            }
        }
}
