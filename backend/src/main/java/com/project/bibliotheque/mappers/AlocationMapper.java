package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.AlocationDto;
import com.project.bibliotheque.entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface AlocationMapper {
    @Mapping(source = "idTransaction", target = "id")
    @Mapping(source = "document.idDocument", target = "documentId")
    @Mapping(source = "carteClient.idCarteClient", target = "carteId")
    @Mapping(source = "carteClient.client.imageUrl", target = "imageUrl")
    @Mapping(source = "carteClient.client.nom", target = "nomUser")
    @Mapping(source = "carteClient.client.prenom", target = "prenomUser")
    @Mapping(source = "document.titre", target = "titreDocument")
    @Mapping(source = "carteClient.client.email", target = "email")
    @Mapping(source = "dateDebut", target = "dateDebut")
    @Mapping(source = "fraixExige", target = "fraixExige")
    @Mapping(source = "carteClient.client.id", target = "userId")
    AlocationDto toDto(Location alocation);
    Location toEntity(AlocationDto alocationDto);
}
