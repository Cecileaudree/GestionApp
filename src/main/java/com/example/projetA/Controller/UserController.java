package com.example.projetA.Controller;

import com.example.projetA.Dto.UserDto;
import com.example.projetA.Entity.User;
import com.example.projetA.Services.AppareilsService;
import com.example.projetA.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppareilsService appareilsService;

    @RequestMapping("/login")
    public String loginForm() {
        return "pages-login";
    }

    /*@RequestMapping("/login-error")
    public String loginError(Model model, Employe employe) {
        model.addAttribute("loginError", true);
        return "login";
    }*/

    @RequestMapping("/forgot-password")
    public String forgotpassword() {
        return "forgot-password";
    }

    /*@GetMapping("/index")
    public String showAcceuil(Model model) {
        List <Appareils> list = appareilsService.List_appareils();
        System.out.println(list.size());
        //logger.info("Nombre d'appareils récupérés : " + list.size());
        model.addAttribute("appareils", list);
        return "index";
    }*/

    @GetMapping("/create_User")
    public String Show_create_User(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("User",userDto);
        return "pages-register";
    }


    @PostMapping("/create_User")
    //@ResponseStatus(HttpStatus.CREATED)
    public String  create_User(@ModelAttribute UserDto userDto) throws Exception {
        User user= new User();
        System.out.println(userDto.getName());
        user = userService.create_User(userDto);
        System.out.println(user);
        //return "hello";
        return "redirect:/accueil";
    }

    @GetMapping("/list_user")
    // @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String  List_User(Model model){
        List<User> list = userService.List_User();
        //model.addAttribute("appareils", list);
        return "list";
    }

    @PostMapping("/delete/{idUser}")
    public ResponseEntity<String> DeleteUser(@PathVariable Long idUser){
        try {
            userService.SupprimerUser(idUser);
            return  ResponseEntity.ok("User supprimer avec succes");
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update/{idUser}")
    public User ModifierUser(@PathVariable Long idUser, @RequestBody UserDto userDto){
        User user  = new User();
        // try {
        user = userService.modifierUser(idUser, userDto);
        return  user;

    }


}
