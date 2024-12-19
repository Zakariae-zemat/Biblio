package com.project.bibliotheque.services;

import com.project.bibliotheque.dtos.CarteClientDto;
import com.project.bibliotheque.dtos.DocumentDto;
import com.project.bibliotheque.dtos.PretDto;
import com.project.bibliotheque.entities.CarteClient;
import com.project.bibliotheque.entities.Document;
import com.project.bibliotheque.entities.Notification;
import com.project.bibliotheque.entities.Pret;
import com.project.bibliotheque.mappers.CarteClientMapper;
import com.project.bibliotheque.mappers.DocumentMapper;
import com.project.bibliotheque.mappers.PretMapper;
import com.project.bibliotheque.repositories.PretRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PretService {
    private final PretRepository pretRepository;
    private final DocumentMapper documentMapper;
    private final CarteClientMapper carteClientMapper;
    private final PretMapper pretMapper;
    public PretService(PretRepository pretRepository, DocumentMapper documentMapper, CarteClientMapper carteClientMapper, PretMapper pretMapper) {
        this.pretRepository = pretRepository;
        this.pretMapper = pretMapper;
        this.documentMapper = documentMapper;
        this.carteClientMapper = carteClientMapper;
    }
    public Pret addPret(DocumentDto documentDto, CarteClientDto carteClientDto){
        Pret pret = new Pret();
        Document document = documentMapper.toEntity(documentDto);
        CarteClient carteClient = carteClientMapper.toCarteClient(carteClientDto);
        pret.setDocument(document);
        pret.setCarteClient(carteClient);
        pret.setFraixRetard(5);
        pret.setDateDebut(java.sql.Date.valueOf(java.time.LocalDate.now()));
        if(document.isEstFortementdemander()){
            pret.setDateFin(java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(7)));
        }else{
            pret.setDateFin(java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(21)));
        }
        Notification notification = new Notification();
        notification.setMessage("Votre pret du ".concat(documentDto.getTitre()).concat(" a été enregistré avec succès"));
        notification.setRecepteur(carteClient.getClient());
        notification.setEstVue(false);
        return pretRepository.save(pret);
    }
    public Pret updatePret(Pret pret){
        return pretRepository.save(pret);
    }
    public Pret getPretById(Long id){
        return pretRepository.findById(id).orElse(null);
    }
    public List<PretDto> getAllPrets(){
        List<Pret> prets = pretRepository.findAll();

        List<PretDto> pretDtos =  prets.stream().map(pretMapper::toDto).collect(Collectors.toList());
        pretDtos.forEach(PretDto::updateStatuePret);
        return pretDtos;
    }
    public void deletePretById(Long id){
        pretRepository.deleteById(id);
    }
}
