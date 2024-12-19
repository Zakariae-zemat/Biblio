package com.project.bibliotheque.dtos;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private Long documentId;
    private Long carteId;
    private String imageUrl; //user profile
    private String nomUser; //user name
    private String prenomUser; //user last name
    private String titreDocument;//titre du document
    private String email;
    private Long userId;
    private String type;
}

