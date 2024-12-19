package com.project.bibliotheque.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DiscriminatorValue("Client")
public class Client extends Utilisateur {
     private Long montantTotal= 0L;

     @Override
     public String toString() {
          if (this.getCarteClient() != null) {
               return "addresse= ".concat(this.getAddresse())
                       .concat(" idCart= ").concat(this.getCarteClient().getIdCarteClient().toString())
                       .concat(" idClient = ").concat(this.getId().toString())
                       .concat(" nom= ").concat(this.getNom())
                       .concat(" prenom= ").concat(this.getPrenom())
                       .concat(" estRegistered=").concat(String.valueOf(this.isEstRegistered()));
          } else {
               return "addresse= ".concat(this.getAddresse())
                       .concat(" idClient = ").concat(this.getId().toString())
                       .concat(" nom= ").concat(this.getNom())
                       .concat(" prenom= ").concat(this.getPrenom())
                       .concat(" estRegistered=").concat(String.valueOf(this.isEstRegistered()));
          }
     }
}