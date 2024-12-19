package com.project.bibliotheque.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

@DiscriminatorValue("RapportAnnuelle")

public class RapportAnnuelle extends Rapport{
    private int annee;
}
