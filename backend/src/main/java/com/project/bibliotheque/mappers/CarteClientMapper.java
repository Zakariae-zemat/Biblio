package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.CarteClientDto;
import com.project.bibliotheque.entities.CarteClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface  CarteClientMapper {
    @Mapping(source = "idCarteClient", target = "idCarteClient")
    @Mapping(source= "client.id", target = "idClient")
    CarteClientDto toCarteClientDto(CarteClient carteClient);
    CarteClient toCarteClient(CarteClientDto carteClientDto);
}
