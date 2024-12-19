package com.project.bibliotheque.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long idDocument;
    private String titre;
    private String auteur;
    private String description;
    private String imgUrl;
    private Long fraixExige;
    private Integer nombreExemplaire = 0;
    private boolean estFortementdemander;
    @Temporal(TemporalType.DATE)
    private Date datePublication;
    private String langue;
    private boolean estPretable;
    @Enumerated(EnumType.STRING)
    private EtatDocument etat;
    @ManyToOne
    @JoinColumn(name = "bibliotheque_id")
    private Bibliotheque bibliotheque;
    @OneToMany(mappedBy = "document")
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "document")
    private List<Rapport> rapports;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "discripteur_documents",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "discripteur_id")
    )
    private List<Discripteur> discripteurs = new ArrayList<>();
}
