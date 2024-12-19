package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.TransactionDto;
import com.project.bibliotheque.services.TransactionService;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @GetMapping("/user")
    public List<TransactionDto> getAllTransactionsByUserEmail(@RequestParam String email) {
        return transactionService.getAllTransactionsByUserEmail(email);
    }
}
