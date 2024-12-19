package com.project.bibliotheque.entities;

import com.fasterxml.jackson.databind.annotation.EnumNaming;


public enum EtatDocument {
    CREE("Créé"),
    EN_COURS_DE_REDAC("En cours de rédaction"),
    EN_ATTENTE_VALIDATION("En attente de validation"),
    VALIDE("Validé"),
    EN_ATTENTE_REVISION("En attente de révision"),
    EN_COURS_DE_REVISION("En cours de révision"),
    ARCHIVE("Archivé"),
    SUPPRIME("Supprimé"),
    DETRUIT("Détruit");
    private String libelle;

    EtatDocument(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
