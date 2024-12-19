package com.project.bibliotheque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@AllArgsConstructor
@DiscriminatorValue("Bibliothecaire")

public class Bibliothecaire extends Utilisateur {
    @ManyToOne
    @JoinColumn(name = "id_bibliotheque")
    private Bibliotheque bibliotheque;
    @OneToMany(mappedBy = "emeteur",cascade=CascadeType.ALL)
    private List<Notification> notificationsEmeteur;
    private String status;
}
