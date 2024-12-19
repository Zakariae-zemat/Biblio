package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.Document;
import com.project.bibliotheque.entities.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RapportRepository extends JpaRepository<Rapport, Long> {
    List<Rapport> findRapportByDateStatistique(Date dateStatistique);
    Rapport findRapportByDateStatistiqueAndDocument(Date dateStatistique, Document document);
}
