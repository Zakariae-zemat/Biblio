package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
