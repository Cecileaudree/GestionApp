package com.example.projetA.Entity;


import com.example.projetA.Enumeration.StatutAppareils;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Table(name="Appareils")
@Builder
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAppareils")
public class Appareils implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppareils;

    private String designation;
    private String nom;
    private String marque;
    private String ref;
    private String fabriquant;
    private String numeroSerie;

    private LocalDate dateService;
    private String etat;
    private String affectation;
    private LocalDate datefin;

    private boolean istaken;
    private boolean utilise;

    //private String rapport_F1;
    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutAppareils statut;

    /*@JsonBackReference
    @OneToOne(mappedBy = "appareils", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  RapportF1 rapports = new RapportF1();*/
    @JsonBackReference
    @OneToOne(mappedBy = "appareils", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private RapportF1 rapportF1;

}
