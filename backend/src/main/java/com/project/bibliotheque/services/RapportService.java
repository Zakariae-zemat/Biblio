package com.project.bibliotheque.services;

import com.project.bibliotheque.dtos.RapportDto;
import com.project.bibliotheque.entities.Document;
import com.project.bibliotheque.entities.Rapport;
import com.project.bibliotheque.mappers.RapportMapper;
import com.project.bibliotheque.repositories.DocumentRepository;
import com.project.bibliotheque.repositories.RapportRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RapportService {
    private final RapportMapper rapportMapper;
    private final DocumentRepository documentRepository;
    private final RapportRepository rapportRepository;

    public RapportService(DocumentRepository documentRepository, RapportRepository rapportRepository, RapportMapper rapportMapper) {
        this.documentRepository = documentRepository;
        this.rapportRepository = rapportRepository;
        this.rapportMapper = rapportMapper;
    }
    //add a new rapport
    public RapportDto addRapport(Date dateStatistique, String typeStatistique,Long idDocument){
        Document document = documentRepository.findById(idDocument).orElse(null);
        Rapport rapport = rapportRepository.findRapportByDateStatistiqueAndDocument(dateStatistique, document);
        if(rapport == null){
            rapport = new Rapport();
            rapport.setDateStatistique(dateStatistique);
            rapport.setDocument(document);
            if(typeStatistique.equals("emprunt")) {
                rapport.setNombrePret(1);
            }else if(typeStatistique.equals("perdu")){
                rapport.setNombrePerdu(1);
            }else if(typeStatistique.equals("reservation")){
                rapport.setNombreReservation(1);
            }else{
                if(typeStatistique.equals("location")){
                    rapport.setNombreLocation(1);
                }else{
                    rapport.setNombreRetour(1);
                }
            }
        }else{
            if(typeStatistique.equals("emprunt")) {
                rapport.setNombrePret(rapport.getNombrePret()+1);
            }else if(typeStatistique.equals("perdu")){
                rapport.setNombrePerdu(rapport.getNombrePerdu()+1);
            }else if(typeStatistique.equals("reservation")){
                rapport.setNombreReservation(rapport.getNombreReservation()+1);
            }else{
                if(typeStatistique.equals("location")){
                    rapport.setNombreLocation(rapport.getNombreLocation()+1);
                }else{
                    rapport.setNombreRetour(rapport.getNombreRetour()+1);
                }
            }
        }
        rapportRepository.save(rapport);
        return rapportMapper.toDto(rapport);

    }
    //get a rapport by id
    public RapportDto getRapportById(Long id){
        return rapportMapper.toDto(rapportRepository.findById(id).orElse(null));
    }
    //get Rapport par jour
    public List<RapportDto> getRapportByDateStatistique(Date dateStatistique){
        List<Rapport> rapports = rapportRepository.findRapportByDateStatistique(dateStatistique);
        return rapports.stream().map(rapportMapper::toDto).collect(Collectors.toList());
    }
    //get Rapport par Mois and Year
    public List<RapportDto> getRapportByMonth(int month, int year){
        List<Rapport> rapports = rapportRepository.findAll();
        return rapports.stream()
                .filter(rapport -> {
                    LocalDate localDate = rapport.getDateStatistique().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return localDate.getMonthValue() == month && localDate.getYear() == year;
                })
                .map(rapportMapper::toDto)
                .collect(Collectors.toList());
    }
    //get Rapport par Year
    public List<RapportDto> getRapportByYear(int year){
        List<Rapport> rapports = rapportRepository.findAll();
        return rapports.stream()
                .filter(rapport -> {
                    LocalDate localDate = rapport.getDateStatistique().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return localDate.getYear() == year;
                })
                .map(rapportMapper::toDto)
                .collect(Collectors.toList());
    }
    //get all rapports
    public List<RapportDto> getAllRapports(){
        List<Rapport> rapports = rapportRepository.findAll();
        return rapports.stream().map(rapportMapper::toDto).collect(Collectors.toList());
    }
}