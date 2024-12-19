package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.BibliothequeDto;
import com.project.bibliotheque.entities.Bibliotheque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BibliothequeMapper {
    @Mapping(source = "id", target = "id")
    BibliothequeDto toDto(Bibliotheque bibliotheque);
    Bibliotheque toEntity(BibliothequeDto bibliothequeDto);
}