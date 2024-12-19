package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.CarteClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartClientRepository extends JpaRepository<CarteClient,Long> {
}
