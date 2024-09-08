package com.example.projetA.Controller;

import com.example.projetA.Dto.AppareilsDto;
import com.example.projetA.Entity.Appareils;
import com.example.projetA.Repository.AppareilsRepository;
import com.example.projetA.Services.AppareilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class AppareilsController {


    @Autowired
    private AppareilsService appareilsService;

    @Autowired
    private AppareilsRepository appareilsRepository;

    //private static final Logger logger = Logger.(AppareilsController.class.getName());




    @GetMapping("/create")
    public String pageCreate_Appareils(Model model){
        AppareilsDto appareilsDto = new AppareilsDto();
       model.addAttribute("appareilsDto",appareilsDto);
       // System.out.println(appareilsDto);
        return "forms-layouts";
    }

    @GetMapping("/accueil")
    public String showAcceuil(Model model) {
        List<Appareils> list = appareilsService.List_appareils();
        //List<Appareils> list1 = appareilsService.List_appareils_istaken();
       // List<Appareils> list2 = appareilsService.List_appareils_istaken_isUse();
        //System.out.println(list1.size() + "," +list2.size());
        int a = list.size();
        System.out.println(list.size());
        //logger.info("Nombre d'appareils récupérés : " + list.size());
        model.addAttribute("appareils", list);
        model.addAttribute("numbAppa",a);
        return "index";
    }

    @GetMapping("/reparation")
    public String reparation(Model model) {
        List<Appareils> list = appareilsService.List_appareils_istaken();
        int a= list.size();
        System.out.println(list.size());
        //logger.info("Nombre d'appareils récupérés : " + list.size());
        model.addAttribute("appareils", list);
        model.addAttribute("nubReparation", a);
        return "liste_reparation";
    }

    @GetMapping("/broke")
    public String broke(Model model) {
        List<Appareils> list = appareilsService.List_appareils_istaken_isUse();
        System.out.println(list.size() );
        int a = list.size();
        //System.out.println(list.size());
        //logger.info("Nombre d'appareils récupérés : " + list.size());
        model.addAttribute("appareils", list);
        model.addAttribute("nubBroke",a);
        return "liste_broke";
    }


    @PostMapping("/save")
    //@ResponseStatus(HttpStatus.CREATED)
    public String create_Appareils(@ModelAttribute  AppareilsDto appareilsDto, @RequestParam("rapportF1") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException
            {
        Appareils appareils= new Appareils();
        //System.out.println(appareilsDto.getAffectation());
        appareils = appareilsService.create_appareils(appareilsDto , file);
        //System.out.println(appareils);
       // String a = "accueil";
       redirectAttributes.addFlashAttribute("message", "Appareil créé avec succès !");
       redirectAttributes.addFlashAttribute("appareil", appareils);
        //return  new RedirectView("/accueil");
        return "redirect:/accueil";
    }



    @PostMapping("/delete/{idAppareils}")
    public ResponseEntity<String> DeleteAppareils(@PathVariable Long idAppareils){
        try {
            appareilsService.SupprimerAppareils(idAppareils);
            return  ResponseEntity.ok("Appareils supprimer avec succes");
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/edit")
    public String ModifieApp(Model model , @RequestParam Long id){
        Optional<Appareils> optionalAppareils = appareilsRepository.findAllByidAppareils(id);
        AppareilsDto appareilsDto = new AppareilsDto();
        if(optionalAppareils.isPresent()){
            Appareils appareils =optionalAppareils.get();
            appareilsDto.setNom(appareils.getNom());
            appareilsDto.setEtat(appareils.getEtat());
            appareilsDto.setAffectation(appareils.getAffectation());
            // appareils.setRapport_F1(appareilsDto.getRapport_F1());
            appareilsDto.setDesignation(appareils.getEtat());
            appareilsDto.setMarque(appareils.getMarque());
            appareilsDto.setFabriquant(appareils.getFabriquant());
            appareilsDto.setDateService(appareils.getDateService());
            appareilsDto.setNumeroSerie(appareils.getNumeroSerie());
            appareilsDto.setDateService(appareils.getDateService());
            appareilsDto.setDatefin(appareils.getDatefin());

            appareilsRepository.save(appareils);

            model.addAttribute("appareilsDto",appareilsDto);
            model.addAttribute("appareilId",id);
            return "Modifier_Appareil";
        }
        else {
            throw new RuntimeException("Appareils n'a pas ete trouvé");
        }


    }

    @PostMapping("/update")
    public String ModifierAppareils(@RequestParam Long idAppareils, @ModelAttribute AppareilsDto appareilsDto, @RequestParam("rapportF1") MultipartFile file,
                                    RedirectAttributes redirectAttributes){
        Appareils appareils = new Appareils();

        try {
            appareils = appareilsService.modifierAppareils(idAppareils, appareilsDto, file);
            redirectAttributes.addFlashAttribute("message", "Appareil modifié avec succès !");
            return "redirect:/accueil"; // Redirection vers la page souhaitée après modification
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/edit?id=" + idAppareils; // Retour à la page d'édition en cas d'erreur
        }

    }

}
