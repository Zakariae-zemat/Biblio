package com.project.bibliotheque.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BibliothecaireDto extends UtilisateurDto {
    private Long idBibliotheque;
    private String status;
}
