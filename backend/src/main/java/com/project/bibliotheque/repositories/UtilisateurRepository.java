package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    Utilisateur findByNom(String nom);
    Utilisateur findByEmail(String email);
}
