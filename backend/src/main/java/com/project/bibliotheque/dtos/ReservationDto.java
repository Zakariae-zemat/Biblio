package com.project.bibliotheque.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto extends TransactionDto{
    private Date dateReservation;
    private Time heureReservation;
    private String typeReservation;
    private String statue;
}
