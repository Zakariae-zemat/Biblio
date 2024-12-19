package com.project.bibliotheque.services;


import com.project.bibliotheque.dtos.ReservationDto;
import com.project.bibliotheque.entities.CarteClient;
import com.project.bibliotheque.entities.Document;
import com.project.bibliotheque.entities.Reservation;
import com.project.bibliotheque.mappers.ReservationMapper;
import com.project.bibliotheque.repositories.CartClientRepository;
import com.project.bibliotheque.repositories.DocumentRepository;
import com.project.bibliotheque.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CartClientRepository cartClientRepository;
    private final DocumentRepository documentRepository;
    private final ReservationMapper reservationMapper;
    public ReservationService(ReservationRepository reservationRepository, CartClientRepository cartClientRepository,DocumentRepository documentRepository, ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.cartClientRepository = cartClientRepository;
        this.documentRepository = documentRepository;
    }
    public Reservation addReservation(Long idDocument, Long idCarte,String typeReservation){
        Reservation reservation = new Reservation();
        CarteClient carteClient = cartClientRepository.findById(idCarte).orElse(null);
        reservation.setCarteClient(carteClient);
        assert carteClient != null;
        carteClient.setNbrEmprunte(carteClient.getNbrEmprunte()+1);
        Document document = documentRepository.findById(idDocument).orElse(null);
        reservation.setDocument(document);
        // Get the current date
        java.util.Date currentDate = java.sql.Date.valueOf(LocalDate.now());
        reservation.setDateReservation(currentDate);

        // Get the current time
        java.sql.Time currentTime = java.sql.Time.valueOf(LocalTime.now());
        reservation.setHeureReservation(currentTime);
        reservation.setTypeReservation(typeReservation);
        cartClientRepository.save(carteClient);
        return reservationRepository.save(reservation);
    }
    public ReservationDto updateReservation(ReservationDto reservationDto){
        Reservation reservation = reservationMapper.toEntity(reservationDto);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }
    public ReservationDto getReservationById(Long id){
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        return reservationMapper.toDto(reservation);
    }
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(reservationMapper::toDto).collect(Collectors.toList());
    }
    public void deleteReservationById(Long id){
        reservationRepository.deleteById(id);
    }

}
