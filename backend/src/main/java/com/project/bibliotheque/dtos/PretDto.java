package com.project.bibliotheque.dtos;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PretDto extends TransactionDto {
    private Date dateDebut;
    private Date dateFin;
    private int fraixRetard;
    private String statuePret ;
    public void updateStatuePret() {
        Date now = new Date();
        if (dateFin.after(now)) {
            statuePret = "En_Cour";
        } else {
            statuePret = "En_Retard";
        }
    }
}
