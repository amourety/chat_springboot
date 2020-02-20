package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.itis.dto.MessageDto;
import ru.itis.services.ChatService;

@Controller
public class MessagesController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    private ChatService service;

    @MessageMapping("/message")
    public void saveMessage(@Payload MessageDto message) {
        service.addMessage(message);
        template.convertAndSend("/chat/" + message.getChatId(), message);
    }
}
