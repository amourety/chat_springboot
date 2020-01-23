package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.itis.dto.MessageDto;
import ru.itis.dto.RegisterDto;
import ru.itis.services.ChatService;
import ru.itis.services.UserService;

import java.util.List;

@Controller
public class MessagesController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    private ChatService service;

    @MessageMapping("/message")
    public void  saveMessage(@Payload MessageDto message){
        service.addMessage(message);
        template.convertAndSend("/chat/" + message.getChatId(), message);
    }
}
