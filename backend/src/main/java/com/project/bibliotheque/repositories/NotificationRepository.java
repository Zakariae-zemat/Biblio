package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecepteurId(Long recepteur);
}
