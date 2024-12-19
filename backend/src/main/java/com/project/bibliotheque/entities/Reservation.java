package com.project.bibliotheque.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@DiscriminatorValue("Reservation")

public class Reservation extends Transaction {
    @Temporal(TemporalType.DATE)
    private Date dateReservation;
    @Temporal(TemporalType.TIME)
    private Time heureReservation;
    private String typeReservation;
    private String statue = "EnTraitement";
}
