package com.project.bibliotheque.services;

import com.project.bibliotheque.entities.RapportAnnuelle;
import com.project.bibliotheque.repositories.RapportAnnuelleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapportAnnuelleService {
    private final RapportAnnuelleRepository rapportAnnuelleRepository;

    public RapportAnnuelleService(RapportAnnuelleRepository rapportAnnuelleRepository) {
        this.rapportAnnuelleRepository = rapportAnnuelleRepository;
    }
    public RapportAnnuelle addRapportAnnuelle(RapportAnnuelle rapportAnnuelle){
        return rapportAnnuelleRepository.save(rapportAnnuelle);
    }
    public RapportAnnuelle updateRapportAnnuelle(RapportAnnuelle rapportAnnuelle){
        return rapportAnnuelleRepository.save(rapportAnnuelle);
    }
    public RapportAnnuelle getRapportAnnuelleById(Long id){
        return rapportAnnuelleRepository.findById(id).orElse(null);
    }
    public List<RapportAnnuelle> getAllRapportAnnuelles(){
        return rapportAnnuelleRepository.findAll();
    }
    public void deleteRapportAnnuelleById(Long id){
        rapportAnnuelleRepository.deleteById(id);
    }

}
