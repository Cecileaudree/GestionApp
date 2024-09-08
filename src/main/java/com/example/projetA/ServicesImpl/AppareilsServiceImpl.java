package com.example.projetA.ServicesImpl;

import com.example.projetA.Dto.AppareilsDto;
import com.example.projetA.Entity.Appareils;
import com.example.projetA.Entity.RapportF1;
import com.example.projetA.Enumeration.StatutAppareils;
import com.example.projetA.Repository.AppareilsRepository;
import com.example.projetA.Repository.RapportF1Repository;
import com.example.projetA.Services.AppareilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppareilsServiceImpl implements AppareilsService {

    @Autowired
    private AppareilsRepository appareilsRepository;

    @Autowired
    private RapportF1Repository rapportF1Repository;

    @Autowired
    private MailService mailService;

    private static final Logger logger = LoggerFactory.getLogger(AppareilsServiceImpl.class);

    /*Override
    public Appareils create_appareils(AppareilsDto appareilsDto, MultipartFile file){
        String filename = file.getOriginalFilename();

        try {
            if(filename.contains("..")) {
                throw new IllegalArgumentException("le nom du fichier est invalide");
            }
            Appareils appareils1 = new Appareils();
            Appareils appareils2 = Appareils.builder()
                    .dateService(appareilsDto.getDateService())
                    .marque(appareilsDto.getMarque())
                    .affectation(appareilsDto.getAffectation())
                    .designation(appareilsDto.getDesignation())
                    .etat(appareilsDto.getEtat())
                    .nom(appareilsDto.getNom())
                    .fabriquant(appareilsDto.getFabriquant())
                    .numeroSerie(appareilsDto.getNumeroSerie())
                    .ref(appareilsDto.getRef())
                    .statut(StatutAppareils.Active)
                    .build();



            RapportF1 rapportF1 = new RapportF1();
            rapportF1.setFilename(filename);
            rapportF1.setFiletype(file.getContentType());

            rapportF1.setContenu(file.getBytes());
            System.out.println(rapportF1);
            rapportF1.setAppareils(appareils2);
            appareils2.setRapports(rapportF1);
            rapportF1= rapportF1Repository.save(rapportF1);
            appareils1= appareilsRepository.save(appareils2);
            System.out.println(rapportF1);

            return appareils1;


        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du fichier : " + e.getMessage(), e);
        }



    }*/

    @Override
    public Appareils create_appareils(AppareilsDto appareilsDto, MultipartFile file ) {
        String filename = file.getOriginalFilename();

        try {
            // Validation du nom de fichier
            if (filename == null || filename.contains("..")) {
                throw new IllegalArgumentException("Le nom du fichier est invalide");
            }

            // Création de l'objet Appareils
            Appareils appareils1= new Appareils();
            Appareils appareils = Appareils.builder()
                    .dateService(appareilsDto.getDateService())
                    .marque(appareilsDto.getMarque())
                    .affectation(appareilsDto.getAffectation())
                    .designation(appareilsDto.getDesignation())
                    .etat(appareilsDto.getEtat())
                    .nom(appareilsDto.getNom())
                    .fabriquant(appareilsDto.getFabriquant())
                    .numeroSerie(appareilsDto.getNumeroSerie())
                    .ref(appareilsDto.getRef())
                    .istaken(false)
                    .utilise(false)
                    .statut(StatutAppareils.Active)
                    .datefin(appareilsDto.getDatefin())
                    .build();

            // Création de l'objet RapportF1
            RapportF1 rapportF12=new RapportF1();
            RapportF1 rapportF1 = new RapportF1();
            rapportF1.setFilename(filename);
            rapportF1.setFiletype(file.getContentType());
           // rapportF1.setContenu(file.getBytes());
            rapportF1.setAppareils(appareils);

            // Association du rapport à l'appareil
            appareils.setRapportF1(rapportF1);

            // Sauvegarde du rapport et de l'appareil
            rapportF12 =rapportF1Repository.save(rapportF1);
            //rapportF1 = rapportF1Repository.save(rapportF1);
            appareils1= appareilsRepository.save(appareils);
           // appareils = appareilsRepository.save(appareils);

            logger.info("RapportF1 saved: {}", rapportF1);
            logger.info("Appareils saved: {}", appareils);

            return appareils;

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde de l'appareil : {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la sauvegarde de l'appareil : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Appareils> List_appareils() {
        return appareilsRepository.findAll(Sort.by(Sort.Direction.DESC,"dateService"));
    }

    @Override
    public void  SupprimerAppareils(Long idAppareils){
        Optional<Appareils> optionalAppareils = appareilsRepository.findAllByidAppareils(idAppareils);
        if(optionalAppareils.isPresent()){
            Appareils appareils = optionalAppareils.get();
            appareils.setStatut(StatutAppareils.NonActive);
            appareilsRepository.save(appareils);
        }
        else {
            throw new RuntimeException ("Appareil  n'a pas ete trouvé");
        }

    }

   /* @Override
    public Appareils modifierAppareils(Long idAppareils , AppareilsDto appareilsDto){
        Optional<Appareils> optionalAppareils = appareilsRepository.findAllByidAppareils(idAppareils);
        if(optionalAppareils.isPresent()){
            Appareils appareils =optionalAppareils.get();
            appareils.setNom(appareilsDto.getNom());
            appareils.setEtat(appareilsDto.getEtat());
            appareils.setAffectation(appareilsDto.getAffectation());
           // appareils.setRapport_F1(appareilsDto.getRapport_F1());
            appareils.setDesignation(appareilsDto.getEtat());
            appareils.setMarque(appareilsDto.getMarque());
            appareils.setFabriquant(appareilsDto.getFabriquant());
            appareils.setDateService(appareilsDto.getDateService());
            appareils.setNumeroSerie(appareilsDto.getNumeroSerie());

            appareilsRepository.save(appareils);

            return appareils;
        }
        else {
            throw new RuntimeException("Appareils n'a pas ete trouvé");
        }
    }*/

    public Appareils modifierAppareils(Long idAppareils, AppareilsDto appareilsDto, MultipartFile file) {
        Optional<Appareils> optionalAppareils = appareilsRepository.findAllByidAppareils(idAppareils);
        if (optionalAppareils.isPresent()) {
            Appareils appareils = optionalAppareils.get();

            // Mettre à jour les informations de l'appareil à partir du DTO
            appareils.setNom(appareilsDto.getNom());
            appareils.setEtat(appareilsDto.getEtat());
            appareils.setAffectation(appareilsDto.getAffectation());
            appareils.setDesignation(appareilsDto.getDesignation());  // Correction ici
            appareils.setMarque(appareilsDto.getMarque());
            appareils.setFabriquant(appareilsDto.getFabriquant());
            appareils.setDateService(appareilsDto.getDateService());
            appareils.setNumeroSerie(appareilsDto.getNumeroSerie());

            // Gestion de la mise à jour du fichier
            if (file != null && !file.isEmpty()) {
                try {
                    String filename = file.getOriginalFilename();
                    if (filename != null && filename.contains("..")) {
                        throw new IllegalArgumentException("Le nom du fichier est invalide");
                    }

                    // Si un rapport existait déjà, vous pouvez choisir de le mettre à jour ou de le remplacer
                    RapportF1 rapportF1 = appareils.getRapportF1();
                    if (rapportF1 == null) {
                        rapportF1 = new RapportF1();
                        rapportF1.setAppareils(appareils);  // Associer l'appareil au rapport
                    }

                    rapportF1.setFilename(filename);
                    rapportF1.setFiletype(file.getContentType());
                    rapportF1.setContenu(file.getBytes());

                    // Sauvegarde du rapport
                    rapportF1 = rapportF1Repository.save(rapportF1);

                    // Associer le nouveau rapport à l'appareil
                    appareils.setRapportF1(rapportF1);
                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de la lecture du fichier : " + e.getMessage(), e);
                }
            }

            // Sauvegarder l'entité `Appareils`
            appareilsRepository.save(appareils);

            return appareils;
        } else {
            throw new RuntimeException("Appareil non trouvé");
        }
    }


    // @Scheduled(cron = "0 0 9 */3 * ?") // Planifié pour s'exécuter tous les 3 jours à 9h00
   // @Scheduled(cron = "*/15 * * * * *")
    public void sendDueDateNotifications() {
        //LocalDate startDate = LocalDate.now().plusDays(14);
       // LocalDate endDate = LocalDate.now().plusDays(16);
        //List<Appareils> tasks = appareilsRepository.findByDueDateBetween(startDate, endDate);

        //for (Appareils task : tasks) {
            //String subject = "Reminder: Task due soon";
            //String text = "Dear user,\n\nYour task '" + task.getNom() + "' is due on " + task.getDateService() + ".\n\nBest regards,\nYour Team";
            //mailService.sendMail( subject, text);
        //}
        //mailService.sendMail("deumenicecileaudree@gmail.com");
        System.out.println("hello");
    }

    @Override
    public List<Appareils> List_appareils_istaken(){

        return appareilsRepository.findByIstakenTrue();
    }

    @Override
    public List<Appareils> List_appareils_istaken_isUse(){

        return appareilsRepository.findByIstakenTrueAndUtiliseTrue();
    }



}
