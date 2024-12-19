package com.project.bibliotheque.dtos;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
    private Long idDocument;
    private String titre;
    private String auteur;
    private String type;
    private String description;
    private String imgUrl;
    private Date datePublication;
    private String langue;
    private boolean estPretable;
    private Long fraixExige;
    private boolean estFortementdemander;
    private Integer nombreExemplaire;
    private String etat;
    private Long bibliothequeId;
    private List<TransactionDto> transactions;
    private List<RapportDto> rapports;
    private List<DiscripteurDto> discripteurs;
}
