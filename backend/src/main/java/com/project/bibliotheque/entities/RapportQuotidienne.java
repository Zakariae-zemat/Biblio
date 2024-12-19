package com.project.bibliotheque.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedEntityGraph;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@DiscriminatorValue("RapportQuotidienne")

public class RapportQuotidienne extends Rapport{
    private int annee;
    private int jour;
    private int mois;
}
