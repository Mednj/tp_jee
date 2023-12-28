package com.naja.pfe1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String entreprise;
    private String techno;
    private String status;
    @ManyToMany(mappedBy = "offres")
    private List<Etudiant> Etudiants;

}
