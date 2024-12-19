package com.project.bibliotheque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bibliotheque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ville;
    private String libelle;
    private String tel;
    private String email;
    @OneToMany(mappedBy = "bibliotheque",cascade=CascadeType.ALL)
    private List<Document> Documents;
    @OneToMany(mappedBy = "bibliotheque",cascade=CascadeType.ALL)
    private List<Bibliothecaire> Bibliothecaires;
}
