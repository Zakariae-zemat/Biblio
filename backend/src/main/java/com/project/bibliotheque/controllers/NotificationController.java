package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.NotificationDto;
import com.project.bibliotheque.entities.Notification;
import com.project.bibliotheque.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications(){
        return notificationService.getAllNotifications();
    }
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserId(@RequestParam Long recepteurId){
        try{
            List<NotificationDto> notifications = notificationService.getNotificationsByRecepteurId(recepteurId);
            return ResponseEntity.ok().body(notifications);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/{id}")
    public NotificationDto getNotificationById(Long id){
        return notificationService.getNotificationById(id);
    }
    @PostMapping
    public NotificationDto addNotification(NotificationDto notificationDto){
        return notificationService.addNotification(notificationDto);
    }
    @PostMapping("/sendFromLocation")
    public Notification sendNotification(@RequestBody Map<Object,Object> body){
        String message = (String) body.get("message");
        Long recepteurId = Long.parseLong(body.get("userId").toString());
        String emetteurEmail = (String) body.get("bibliothecaireEmail");
        LocalDate dateDebut = null;
        Object dateDebutObj = body.get("dateDepart");
        if (dateDebutObj instanceof LocalDate) {
            dateDebut = (LocalDate) dateDebutObj;
        } else if (dateDebutObj instanceof String) {
            dateDebut = LocalDate.parse((String) dateDebutObj);
        }
        LocalDate dateFin = null;
        Object dateFinObj = body.get("dateRetour");
        if (dateFinObj instanceof LocalDate) {
            dateFin = (LocalDate) dateFinObj;
        } else if (dateDebutObj instanceof String) {
            dateFin = LocalDate.parse((String) dateFinObj);
        }
        Long documentId = Long.parseLong(body.get("documentId").toString());
        assert dateFin != null;
        return notificationService.sendNotificationFromLocation(message, recepteurId, emetteurEmail, dateDebut, dateFin, documentId);
    }
    @PostMapping("/sendFromPret")
    public Notification sendNotificationFromPret(@RequestBody Map<Object,Object> body){
        String message = (String) body.get("message");
        Long recepteurId = Long.parseLong(body.get("userId").toString());
        String emetteurEmail = (String) body.get("bibliothecaireEmail");
        LocalDate dateDebut = null;
        Object dateDebutObj = body.get("dateDepart");
        if (dateDebutObj instanceof LocalDate) {
            dateDebut = (LocalDate) dateDebutObj;
        } else if (dateDebutObj instanceof String) {
            dateDebut = LocalDate.parse((String) dateDebutObj);
        }
        LocalDate dateFin = null;
        Object dateFinObj = body.get("dateRetour");
        if (dateFinObj instanceof LocalDate) {
            dateFin = (LocalDate) dateFinObj;
        } else if (dateDebutObj instanceof String) {
            dateFin = LocalDate.parse((String) dateFinObj);
        }
        Long documentId = Long.parseLong(body.get("documentId").toString());
        return notificationService.sendNotificationFromPretation(message, recepteurId, emetteurEmail, dateDebut, dateFin, documentId);
    }
    @PutMapping("/{id}")
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto){
        notificationDto.setId(id);
        return notificationService.updateNotification(notificationDto);
    }
    @DeleteMapping("/{id}")
    public void deleteNotificationById(Long id){
        notificationService.deleteNotificationById(id);
    }

}
