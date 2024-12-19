package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
