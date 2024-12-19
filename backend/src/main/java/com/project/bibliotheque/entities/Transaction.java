package com.project.bibliotheque.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long idTransaction;
    @ManyToOne
    @JoinColumn(name = "carteClient_id")
    private CarteClient carteClient;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
}
