package com.project.bibliotheque.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlocationDto extends TransactionDto{
    private Date dateDebut;
    private Date dateFin;
    private int fraixExige;
    private int amountRetard;
    private String statueAlocation;
    public void updateStatueLocation() {
        Date now = new Date();
        if (dateFin.after(now)) {
            statueAlocation = "En_Cour";
        } else {
            statueAlocation = "En_Retard";
        }
    }
}
