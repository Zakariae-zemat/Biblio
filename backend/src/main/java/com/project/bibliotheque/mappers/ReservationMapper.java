package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.ReservationDto;
import com.project.bibliotheque.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface ReservationMapper {
    @Mapping(source = "idTransaction", target = "id")
    @Mapping(source = "document.idDocument", target = "documentId")
    @Mapping(source = "carteClient.idCarteClient", target = "carteId")
    @Mapping(source = "carteClient.client.imageUrl", target = "imageUrl")
    @Mapping(source = "carteClient.client.nom", target = "nomUser")
    @Mapping(source = "carteClient.client.prenom", target = "prenomUser")
    @Mapping(source = "document.titre", target = "titreDocument")
    @Mapping(source = "carteClient.client.email", target = "email")
    @Mapping(source = "dateReservation", target = "dateReservation")
    @Mapping(source = "statue", target = "statue")
    @Mapping(source = "typeReservation", target = "typeReservation")
    ReservationDto toDto(Reservation reservation);
    Reservation toEntity(ReservationDto reservationDto);
}
