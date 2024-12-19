package com.project.bibliotheque.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto extends UtilisateurDto{
    private String adresse;
    private Long idcartClient;
    private boolean estRegistered;
    private Long montantTotal;
    private Integer nbrEmprunte;
}
