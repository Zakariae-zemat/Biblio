package com.project.bibliotheque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discripteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiscripteur;
    private String keyword;
    @ManyToMany(mappedBy = "discripteurs",cascade = CascadeType.ALL)
    private List<Document> documents=new ArrayList<>();
}
