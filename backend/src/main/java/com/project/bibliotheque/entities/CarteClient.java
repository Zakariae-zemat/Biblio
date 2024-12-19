package com.project.bibliotheque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarteClient {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long idCarteClient;
    private String imageUrl;
    private boolean estResident = false;
    private Integer nbrEmprunte = 0;
    private Integer prix = 0;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @OneToOne
    private Utilisateur client;
}
