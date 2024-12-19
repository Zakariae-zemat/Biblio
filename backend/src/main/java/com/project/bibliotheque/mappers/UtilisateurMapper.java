package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.BibliothecaireDto;
import com.project.bibliotheque.dtos.UtilisateurDto;
import com.project.bibliotheque.entities.Bibliothecaire;
import com.project.bibliotheque.entities.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "addresse", target = "addresse")
    @Mapping(source = "estRegistered", target = "estRegistered")
    UtilisateurDto toDto(Utilisateur utilisateur);
    Utilisateur toEntity(UtilisateurDto utilisateurDto);
}
