package com.project.bibliotheque.services;


import com.project.bibliotheque.dtos.TransactionDto;
import com.project.bibliotheque.entities.CarteClient;
import com.project.bibliotheque.entities.Client;
import com.project.bibliotheque.entities.Transaction;
import com.project.bibliotheque.mappers.TransactionMapper;
import com.project.bibliotheque.repositories.ClientRepository;
import com.project.bibliotheque.repositories.TransactionRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRespository transactionRespository;
    private final ClientRepository clientRepository;
    private final TransactionMapper transactionMapper;
    public TransactionService(TransactionRespository transactionRespository, TransactionMapper transactionMapper, ClientRepository clientRepository) {
        this.transactionRespository = transactionRespository;
        this.transactionMapper = transactionMapper;
        this.clientRepository = clientRepository;
    }
    //get all transactions By user
    public List<TransactionDto> getAllTransactionsByUserEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        CarteClient carteClient = client.getCarteClient();
        List<Transaction> transactions = transactionRespository.findByCarteClient(carteClient);
        return transactions.stream().map(transactionMapper::toDto).collect(Collectors.toList());    }
}
