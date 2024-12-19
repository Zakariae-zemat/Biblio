package com.project.bibliotheque.services;

import com.project.bibliotheque.entities.RapportQuotidienne;
import com.project.bibliotheque.repositories.RapportQuotidienneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapportQuotidienneService {
    private final RapportQuotidienneRepository rapportQuotidienneRepository;

    public RapportQuotidienneService(RapportQuotidienneRepository rapportQuotidienneRepository) {
        this.rapportQuotidienneRepository = rapportQuotidienneRepository;
    }
    public RapportQuotidienne addRapportQuotidienne(RapportQuotidienne rapportQuotidienne){
        return rapportQuotidienneRepository.save(rapportQuotidienne);
    }
    public RapportQuotidienne updateRapportQuotidienne(RapportQuotidienne rapportQuotidienne){
        return rapportQuotidienneRepository.save(rapportQuotidienne);
    }
    public RapportQuotidienne getRapportQuotidienneById(Long id){
        return rapportQuotidienneRepository.findById(id).orElse(null);
    }
    public List<RapportQuotidienne> getAllRapportQuotidiennes(){
        return rapportQuotidienneRepository.findAll();
    }
    public void deleteRapportQuotidienneById(Long id){
        rapportQuotidienneRepository.deleteById(id);
    }
}
