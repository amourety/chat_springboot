package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.dto.UserForm;
import ru.itis.services.ChatService;

@Controller
public class RegistrationController {

    @Autowired
    private ChatService service;


    @PostMapping("/registration")
    @PreAuthorize("permitAll()")
    public String registration(UserForm userForm){

        service.signUp(userForm);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String getPage(){
        return "registration";
    }
}
