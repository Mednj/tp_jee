package com.naja.pfe1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pfe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ref;
    private String entreprise;
    private String sujet;
    private Date date_debut;
    private Date date_fin;

    @OneToOne
    private Etudiant etudiant;

    @ManyToOne
    private Encadrant encadrant;
}
