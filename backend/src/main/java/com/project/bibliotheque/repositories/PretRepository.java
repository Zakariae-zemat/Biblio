package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Pret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PretRepository extends JpaRepository<Pret, Long> {
}
