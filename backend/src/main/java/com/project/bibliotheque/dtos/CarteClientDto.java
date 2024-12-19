package com.project.bibliotheque.dtos;

import com.project.bibliotheque.entities.Utilisateur;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteClientDto {
    private Long idCarteClient;
    private String imageUrl;
    private boolean estResident;
    private Integer nbrEmprunte;
    private Integer prix;
    private Date dateFin;
    private Long idClient;
}
