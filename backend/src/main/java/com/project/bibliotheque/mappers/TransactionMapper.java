package com.project.bibliotheque.mappers;

import com.project.bibliotheque.dtos.TransactionDto;
import com.project.bibliotheque.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(expression = "java(transaction.getClass().getSimpleName())", target = "type")
    @Mapping(source = "idTransaction", target = "id")
    //UserId
    @Mapping(source = "carteClient.client.id", target = "userId")
    //nomUser
    @Mapping(source = "carteClient.client.nom", target = "nomUser")
    //prenomUser
    @Mapping(source = "carteClient.client.prenom", target = "prenomUser")
    //documentId
    @Mapping(source = "document.idDocument", target = "documentId")
    //documentTitre
    @Mapping(source = "document.titre", target = "titreDocument")
    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto);
}