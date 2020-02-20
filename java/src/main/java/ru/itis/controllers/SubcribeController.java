package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.dto.RegisterDto;
import ru.itis.services.ChatService;

import java.util.List;

@Controller
public class SubcribeController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    private ChatService service;

    @MessageMapping("/register")
    public void subscribe(@Payload RegisterDto register) {
        if (!service.isUserWithoutChatRoom(register.getUsername())) {
            service.updateUserChat(register.getUsername(), register.getChatId());
        } else {
            service.saveUserToChat(register.getUsername(), register.getChatId());
        }
        template.convertAndSend("/chat/" + register.getChatId() + "/subscribers", service.getChatRoomUserNames(String.valueOf(register.getChatId())));
    }

    @MessageMapping("/notify")
    public void refresh(@Header("CHAT_ID") String chatId) {
        template.convertAndSend("/chat/" + chatId + "/subscribers", service.getChatRoomUserNames(String.valueOf(chatId)));
    }

    @PostMapping("chat/{id}/subscribers")
    @CrossOrigin(origins = "*")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public ResponseEntity<List<String>> getSubscribers(@PathVariable String id) {
        return ResponseEntity.ok(service.getChatRoomUserNames(String.valueOf(id)));
    }
}
