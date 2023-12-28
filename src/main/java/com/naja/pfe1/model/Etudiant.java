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
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String filiere;
    private String groupe;
    private String tele;
    private String email;

    @OneToOne
    private Pfe pfe;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "etudiant_offre",
            joinColumns = @JoinColumn(name = "offreid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "etudiant_id",
                    referencedColumnName = "id"))
    private List<Offre> offres;

}
