package com.project.bibliotheque.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DiscriminatorValue("DisqueCompacte")
public class DisqueCompacte extends Document {
    @Enumerated(EnumType.STRING)
    private FormatExtention format ;
    private int duree;
}
