package com.project.bibliotheque.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapportDto {
    private Long id;
    private String titre;
    private String description;
    private Date dateStatistique;
    private int nombrePret;
    private int nombreRetour;
    private int nombreLocation;
    private int nombrePerdu;
    private int nombreReservation;
    private Long documentId;
    private String documentTitre;
}
