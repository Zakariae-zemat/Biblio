package com.project.bibliotheque.controllers;


import com.project.bibliotheque.dtos.PretDto;
import com.project.bibliotheque.entities.CarteClient;
import com.project.bibliotheque.entities.Document;
import com.project.bibliotheque.entities.Pret;
import com.project.bibliotheque.entities.Rapport;
import com.project.bibliotheque.repositories.CartClientRepository;
import com.project.bibliotheque.repositories.DocumentRepository;
import com.project.bibliotheque.services.PretService;
import com.project.bibliotheque.services.RapportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/prets")
public class PretController {
    private final PretService pretService;
    private final CartClientRepository cartClientRepository;
    private final DocumentRepository documentRepository;
    private final RapportService rapportService;
    public PretController(PretService pretService,DocumentRepository documentRepository,CartClientRepository cartClientRepository,RapportService rapportService){
        this.pretService = pretService;
        this.cartClientRepository = cartClientRepository;
        this.documentRepository = documentRepository;
        this.rapportService = rapportService;
    }
    @GetMapping
    public List<PretDto> getAllPrets(){
        return pretService.getAllPrets();
    }
    @GetMapping("/{id}")
    public Pret getPretById(Long id){
        return pretService.getPretById(id);
    }
    @PutMapping("/{id}")
    public Pret updatePret(Long id, Pret pret){
        pret.setIdTransaction(id);
        return pretService.updatePret(pret);
    }
    @DeleteMapping
    public void deletePretById(@RequestParam Long id){
        //getPretById(id);
        Pret pret = pretService.getPretById(id);
        CarteClient carteClient = pret.getCarteClient();
        carteClient.setNbrEmprunte(carteClient.getNbrEmprunte() - 1);
        Document document = pret.getDocument();
        cartClientRepository.save(carteClient);
        rapportService.addRapport(new java.util.Date(), "retour", document.getIdDocument());
        documentRepository.save(document);
        document.setNombreExemplaire(document.getNombreExemplaire() + 1);
        pretService.deletePretById(id);
    }
}
