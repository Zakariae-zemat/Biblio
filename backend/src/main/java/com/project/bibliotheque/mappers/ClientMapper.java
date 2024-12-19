package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.ClientDto;
import com.project.bibliotheque.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(source = "carteClient.idCarteClient", target = "idcartClient")
    @Mapping(source = "montantTotal", target = "montantTotal")
    @Mapping(source = "carteClient.nbrEmprunte", target = "nbrEmprunte")
    ClientDto toDto(Client client);
    Client toEntity(ClientDto clientDto);
}