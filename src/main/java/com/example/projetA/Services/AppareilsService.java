package com.example.projetA.Services;

import com.example.projetA.Dto.AppareilsDto;
import com.example.projetA.Entity.Appareils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AppareilsService {

    public Appareils create_appareils (AppareilsDto appareilsDto , MultipartFile file) ;

    public List<Appareils> List_appareils();

    public List<Appareils> List_appareils_istaken();

    public List<Appareils> List_appareils_istaken_isUse();

    public void SupprimerAppareils(Long id);

    public Appareils modifierAppareils(Long id, AppareilsDto appareilsDto, MultipartFile file);
    public void EnvoyeEnReparation(Long id);

    public void RetourReparation(Long id);

    public void EnvoyeGate(Long id);


}
