package com.example.projetA.Controller;

import com.example.projetA.ServicesImpl.MailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequiredArgsConstructor
public class MailController {

    @Autowired
    private MailService mailService;


   /* @PostMapping("/send")
    public String SendEmail(@RequestBody Mail email){
        System.out.println(email.getDestination());
        mailService.sendMail(email.getDestination());
        return "Email sent successfully!";

    }
    @PostMapping("/send2")
    public String SendEmail2(@RequestBody Mail email){
        System.out.println(email.getDestination());
        mailService.sendMail(email.getDestination());
        return "Email sent successfully!";

    }*/




}
