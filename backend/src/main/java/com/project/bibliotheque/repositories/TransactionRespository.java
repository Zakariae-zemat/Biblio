package com.project.bibliotheque.repositories;

import com.project.bibliotheque.entities.CarteClient;
import com.project.bibliotheque.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRespository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCarteClient(CarteClient carteClient);
}
