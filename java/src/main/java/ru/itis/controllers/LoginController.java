package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.dto.LoginDto;
import ru.itis.dto.TokenDto;
import ru.itis.dto.TokenValid;
import ru.itis.services.LoginService;


@Controller
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public ResponseEntity<TokenDto> token(@RequestBody LoginDto loginData) {
        return ResponseEntity.ok(service.authenticate(loginData));
    }

    @PostMapping("/chat/valid_token")
    @CrossOrigin(origins = "*")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public TokenValid validToken(@RequestBody TokenDto token) {
        return TokenValid.builder().valid(service.validationByToken(token.getValue())).build();
    }
}
