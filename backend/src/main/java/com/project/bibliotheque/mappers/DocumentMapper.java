package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.DocumentDto;
import com.project.bibliotheque.entities.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    @Mapping(source = "idDocument", target = "idDocument")
    @Mapping(source = "bibliotheque.id", target = "bibliothequeId")
    @Mapping(source = "transactions", target = "transactions")
    @Mapping(source = "rapports", target = "rapports")
    @Mapping(source = "discripteurs", target = "discripteurs")
    @Mapping(expression = "java(document.getClass().getSimpleName())", target = "type")
    @Mapping(source = "etat", target = "etat")
    @Mapping(source = "titre", target = "titre")
    @Mapping(source = "auteur", target = "auteur")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "imgUrl", target = "imgUrl")
    @Mapping(source = "fraixExige", target = "fraixExige")
    @Mapping(source = "nombreExemplaire", target = "nombreExemplaire")
    @Mapping(source = "estFortementdemander", target = "estFortementdemander")
    @Mapping(source = "langue", target = "langue")
    @Mapping(source = "estPretable", target = "estPretable")

    DocumentDto toDto(Document document);
    @Mapping(source = "idDocument", target = "idDocument")
    Document toEntity(DocumentDto documentDto);
}