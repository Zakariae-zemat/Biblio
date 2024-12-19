package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Journale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournaleRepository extends JpaRepository<Journale, Long> {
}
