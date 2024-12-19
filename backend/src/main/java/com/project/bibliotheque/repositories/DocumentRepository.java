package com.project.bibliotheque.repositories;

import com.project.bibliotheque.dtos.DocumentDto;
import com.project.bibliotheque.entities.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findByTitreContaining(String titre, Pageable pageable);
}
