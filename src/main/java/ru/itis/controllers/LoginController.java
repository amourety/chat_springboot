package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.itis.dto.LoginDto;
import ru.itis.dto.TokenDto;
import ru.itis.services.ChatService;

import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Autowired
    private ChatService service;

    @GetMapping("/login")
    public String getPage() {
        return "login";
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public String login(LoginDto loginData)
    {
        if(service.login(loginData)){
            return "redirect:/chat";
        } else return "redirect:/login";

    }

    @PostMapping("/login/token")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public ResponseEntity<TokenDto> token( @RequestBody LoginDto loginData)
    {
        return ResponseEntity.ok(service.token(loginData));
    }
}
