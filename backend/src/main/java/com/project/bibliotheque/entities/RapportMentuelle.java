package com.project.bibliotheque.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

@DiscriminatorValue("RapportMentuelle")

public class RapportMentuelle extends Rapport{
    private int mois;
    private int annee;
}
