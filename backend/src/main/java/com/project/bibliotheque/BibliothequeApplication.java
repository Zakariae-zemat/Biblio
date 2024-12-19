package com.project.bibliotheque;

import com.project.bibliotheque.entities.*;
import com.project.bibliotheque.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BibliothequeApplication implements CommandLineRunner{
    private final UtilisateurRepository utilisateurRepository;
    private BibliothecaireRipository bibliothecaireRipository;
    private BibliothequeRepository bibliothequeRepository;
    private DiscripteurRepository discripteurRepository;
    private LivreRepository documentRepository;
    private NotificationRepository notificationRepository;
    //constructor
    @Autowired
    public BibliothequeApplication(BibliothecaireRipository bibliothecaireRipository, BibliothequeRepository bibliothequeRepository, DiscripteurRepository discripteurRepository, LivreRepository documentRepository, UtilisateurRepository utilisateurRepository, NotificationRepository notificationRepository) {
        this.bibliothecaireRipository = bibliothecaireRipository;
        this.bibliothequeRepository = bibliothequeRepository;
        this.discripteurRepository = discripteurRepository;
        this.documentRepository = documentRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.notificationRepository = notificationRepository;
    }
    public static void main(String[] args) {
        SpringApplication.run(BibliothequeApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        //for dealing with database
    }
}
