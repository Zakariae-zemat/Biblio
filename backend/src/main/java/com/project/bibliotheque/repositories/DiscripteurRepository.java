package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Discripteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface DiscripteurRepository extends JpaRepository<Discripteur, Long> {
}
