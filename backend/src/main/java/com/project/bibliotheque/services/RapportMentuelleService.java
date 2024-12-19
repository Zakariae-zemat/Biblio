package com.project.bibliotheque.services;


import com.project.bibliotheque.entities.RapportMentuelle;
import com.project.bibliotheque.repositories.RapportMentuelleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapportMentuelleService {
    private final RapportMentuelleRepository rapportMentuelleRepository;

    public RapportMentuelleService(RapportMentuelleRepository rapportMentuelleRepository) {
        this.rapportMentuelleRepository = rapportMentuelleRepository;
    }
    public RapportMentuelle addRapportMentuelle(RapportMentuelle rapportMentuelle){
        return rapportMentuelleRepository.save(rapportMentuelle);
    }
    public RapportMentuelle updateRapportMentuelle(RapportMentuelle rapportMentuelle){
        return rapportMentuelleRepository.save(rapportMentuelle);
    }
    public RapportMentuelle getRapportMentuelleById(Long id){
        return rapportMentuelleRepository.findById(id).orElse(null);
    }
    public List<RapportMentuelle> getAllRapportMentuelles(){
        return rapportMentuelleRepository.findAll();
    }
    public void deleteRapportMentuelleById(Long id){
        rapportMentuelleRepository.deleteById(id);
    }
}
