package com.project.bibliotheque.dtos;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {
    private Long id;
    private String nom;
    private String imageUrl;
    private String prenom;
    private String email;
    private String telephone;
    private String cin;
    private String role;
    private String addresse;
    private boolean estRegistered;
    private Date naissance;
}
