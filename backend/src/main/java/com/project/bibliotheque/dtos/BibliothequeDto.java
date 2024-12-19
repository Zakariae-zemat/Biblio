package com.project.bibliotheque.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BibliothequeDto {
    private Long id;
    private String ville;
    private String libelle;
    private String tel;
    private String email;
    private List<DocumentDto> documents;
    private List<BibliothecaireDto> bibliothecaires;
}
