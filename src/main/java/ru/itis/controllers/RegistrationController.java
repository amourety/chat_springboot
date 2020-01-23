package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.UserForm;
import ru.itis.services.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService service;
    @PostMapping("/registration")
    @CrossOrigin(origins = "*")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public ResponseEntity<String> registration(@RequestBody UserForm userForm){
        service.registration(userForm);
        return ResponseEntity.ok("all good");
    }
}
