package com.project.bibliotheque.entities;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private boolean estVue;
    @ManyToOne
    @JoinColumn(name = "emeteur_id")
    private Utilisateur emeteur;
    @ManyToOne
   @JoinColumn(name = "recepteur_id")
    private Utilisateur recepteur;
    private String type;
    private Long montant = 0L;
}
