package com.project.bibliotheque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String nom;
    private String prenom;
    private String password;
    private String role;
    @Column(unique = true)
    private String email;
    private String telephone;
    private String cin;
    private String imageUrl;
    private String addresse;
    @Temporal(TemporalType.DATE)
    private Date naissance;
    private boolean estRegistered = false;
    @OneToMany(mappedBy = "emeteur")
    private List<Notification> notificationsEmeteur;
    @OneToMany(mappedBy = "recepteur")
    private List<Notification> notificationsRecepteur;
    @OneToOne(mappedBy = "client")
    private CarteClient carteClient;
}
