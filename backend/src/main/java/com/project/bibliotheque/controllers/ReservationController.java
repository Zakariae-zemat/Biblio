package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.ReservationDto;
import com.project.bibliotheque.entities.*;
import com.project.bibliotheque.repositories.DocumentRepository;
import com.project.bibliotheque.repositories.PretRepository;
import com.project.bibliotheque.repositories.ReservationRepository;
import com.project.bibliotheque.services.*;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final DocumentRepository documentRepository;
    private final ReservationRepository reservationRepository;
    private final PretService pretService;
    private final LocationService locationService;
    private final CarteClientService carteClientService;
    private final DocumentService documentService;
    private final RapportService rapportService;
    public ReservationController(ReservationService reservationService, DocumentRepository documentRepository, ReservationRepository reservationRepository, PretService pretService, LocationService locationService, CarteClientService carteClientService, DocumentService documentService, RapportService rapportService) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.documentRepository = documentRepository;
        this.pretService = pretService;
        this.locationService = locationService;
        this.carteClientService = carteClientService;
        this.documentService = documentService;
        this.rapportService = rapportService;
    }

    @GetMapping
    public List<ReservationDto> getAllReservations(){
        return reservationService.getAllReservations();
    }
    @GetMapping("/{id}")
    public ReservationDto getReservationById(Long id){
        return reservationService.getReservationById(id);
    }
    @PostMapping("/reserve")
    public Reservation reserve(@RequestBody ReserveRequest request) {
        Long idCart = request.getIdCart();
        Long idDocument = request.getIdDocument();
        String type = request.getType();
        rapportService.addRapport(new Date(), "reservation", idDocument);
        return reservationService.addReservation(idDocument, idCart, type);
    }
    @PutMapping("/statue")
    public ResponseEntity<Map<String,String>> updateReservation(@RequestBody Map<Object,Object> body ){
        Long id = Long.parseLong(body.get("id").toString());
        ReservationDto reservationDto = reservationService.getReservationById(id);
        Document document = documentRepository.findById(reservationDto.getDocumentId()).orElse(null);
        if (reservationDto.getStatue().toLowerCase().compareTo("entraitement")==0) {
            if(document.getNombreExemplaire()>0){
                document.setNombreExemplaire(document.getNombreExemplaire()-1);
                documentRepository.save(document);
                if(reservationDto.getTypeReservation().toLowerCase().compareTo("emprunter")==0){
                    pretService.addPret(documentService.getDocumentById(reservationDto.getDocumentId()), carteClientService.getCarteClientById(reservationDto.getCarteId()));
                    reservationRepository.deleteById(reservationDto.getId());
                    rapportService.addRapport(new Date(), "emprunt", reservationDto.getDocumentId());
                    return ResponseEntity.ok(Map.of("message", "Pret ajouté avec succès"));
                }else{
                    locationService.addLocation(documentService.getDocumentById(reservationDto.getDocumentId()), carteClientService.getCarteClientById(reservationDto.getCarteId()));
                    reservationRepository.deleteById(reservationDto.getId());
                    rapportService.addRapport(new Date(), "location", reservationDto.getDocumentId());
                    return ResponseEntity.ok(Map.of("message", "Location ajoutée avec succès"));
                }
            }else{
                Reservation reservation = reservationRepository.findById(reservationDto.getId()).orElse(null);
                reservation.setStatue("EnAttente");
                reservationRepository.save(reservation);
                return ResponseEntity.ok(Map.of("message", "Document non disponible : En attente"));
            }
        }else{
            if(document.getNombreExemplaire()>0){
                document.setNombreExemplaire(document.getNombreExemplaire()-1);
                documentRepository.save(document);
                if(reservationDto.getTypeReservation().compareTo("emprunter")==0){
                    pretService.addPret(documentService.getDocumentById(reservationDto.getDocumentId()), carteClientService.getCarteClientById(reservationDto.getCarteId()));
                    reservationRepository.deleteById(reservationDto.getId());
                    rapportService.addRapport(new Date(), "emprunt", reservationDto.getDocumentId());
                    return ResponseEntity.ok(Map.of("message", "Pret ajouté avec succès"));
                }else{
                    locationService.addLocation(documentService.getDocumentById(reservationDto.getDocumentId()), carteClientService.getCarteClientById(reservationDto.getCarteId()));
                    reservationRepository.deleteById(reservationDto.getId());
                    rapportService.addRapport(new Date(), "location", reservationDto.getDocumentId());
                    return ResponseEntity.ok(Map.of("message", "Location ajoutée avec succès"));
                }
            }
            return null;
        }
    }
    @DeleteMapping("/{id}")
    public void deleteReservationById(@PathVariable Long id){
        reservationService.deleteReservationById(id);
    }

}

 @Getter
 class ReserveRequest {
    private Long idCart;
    private Long idDocument;
    private String type;

 }
