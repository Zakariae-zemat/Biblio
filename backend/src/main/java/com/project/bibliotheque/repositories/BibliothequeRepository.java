package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Bibliotheque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliothequeRepository extends JpaRepository<Bibliotheque,Long> {

}
