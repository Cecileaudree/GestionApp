package com.example.projetA.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class AppareilsDto {

    private String designation;
    private String nom;
    private String marque;
    private String ref;
    private String fabriquant;
    private String numeroSerie;

   // private boolean istaken;
   // private boolean utilise;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateService;
    private String etat;
    private String affectation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datefin;


    //private MultipartFile rapportF1;

}
