package com.example.projetA.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="Rapport")
public class RapportF1 implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRapport;

    @Column(length = 16777215)
    @Lob
    private byte[] Contenu;

    private String filename;
   // private String filePaths;
    private String filetype;

    /*@OneToOne
    @JoinColumn(name = "idAppareils")
    @JsonBackReference
    private Appareils appareils;*/
    @OneToOne
    @JoinColumn(name = "idAppareils", referencedColumnName = "idAppareils")
    @JsonBackReference
    private Appareils appareils;


}
