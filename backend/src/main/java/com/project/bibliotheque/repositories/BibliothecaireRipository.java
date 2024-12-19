package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Bibliothecaire;
import com.project.bibliotheque.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliothecaireRipository extends JpaRepository<Bibliothecaire,Long> {
    Bibliothecaire findByEmail(String email);
}
