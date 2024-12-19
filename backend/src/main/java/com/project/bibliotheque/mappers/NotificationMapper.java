package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.BibliothecaireDto;
import com.project.bibliotheque.dtos.NotificationDto;
import com.project.bibliotheque.entities.Bibliothecaire;
import com.project.bibliotheque.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(source = "idNotification", target = "id")
    @Mapping(source = "recepteur.id", target = "idRecepteur")
    NotificationDto toDto(Notification notification);
    Notification toEntity(NotificationDto NotificationDto);
}
