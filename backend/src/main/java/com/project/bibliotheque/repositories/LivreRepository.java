package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre,Long> {
}
