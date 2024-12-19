package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.DiscripteurDto;
import com.project.bibliotheque.entities.Discripteur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscripteurMapper {
    @Mapping(source = "idDiscripteur", target = "id")
    DiscripteurDto toDto(Discripteur discripteur);
    @Mapping(target = "documents", ignore = true)
    Discripteur toEntity(DiscripteurDto discripteurDto);
}