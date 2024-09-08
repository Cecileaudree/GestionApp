package com.example.projetA.ServicesImpl;

import com.example.projetA.Entity.Appareils;
import com.example.projetA.Entity.User;
import com.example.projetA.Repository.AppareilsRepository;
import com.example.projetA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppareilsRepository appareilsRepository;

    @Value("$(Asecna_Yaounde_gestion_appareil)")
    private String frommail;

    //@Scheduled(cron = "0 0 8 * * ?")
   // @Scheduled(cron = "*/15 * * * * ?")
    public void sendMail(){
        System.out.println("ok");

        // Récupérer la date limite (J - 15 jours)
        LocalDate limitDateTime = LocalDate.now().plusDays(15);

        // Récupérer les appareils dont la date "datetime" est avant la date limite
        List<Appareils> appareils = appareilsRepository.findByDatefinBefore(limitDateTime);

        if (appareils.isEmpty()) {
            System.out.println("rien");
            // Aucun appareil concerné, rien à envoyer
            return;
        }

        // Construire le contenu de l'email
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Dear user,\n\nHere are the devices that are due soon:\n\n");

        for (Appareils appareil : appareils) {
            emailContent.append("Device: ").append(appareil.getNom())
                    .append("\nDate: ").append(appareil.getDatefin())
                    .append("\n\n");
        }

        emailContent.append("Best regards,\nYour Team");

        // Récupérer tous les emails des utilisateurs
        List<User> users = userRepository.findAll();
        List<String> emails = users.stream()
                .map(User::getEmail)
                .collect(Collectors.toList());

        // Envoyer l'email à tous les utilisateurs
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(frommail);
        simpleMailMessage.setSubject("Reminder: Devices Due Soon");
        simpleMailMessage.setText(emailContent.toString());

        for (String email : emails) {
            simpleMailMessage.setTo(email);
            javaMailSender.send(simpleMailMessage);
        }

        System.out.println("Emails sent to all users.");

        //simpleMailMessage.setTo(emails.toArray(new String[0]));


    }





}
