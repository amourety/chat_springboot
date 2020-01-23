package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;
import ru.itis.models.User;
import ru.itis.services.ChatService;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    ChatService service;

    @GetMapping("/chat/username")
    @CrossOrigin(origins = "*")
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> getUsername() {
         User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         User transferUser = User.builder().username(user.getUsername()).build();
         return ResponseEntity.ok(transferUser);
    }

    @GetMapping("/chat/{id}/messages")
    @CrossOrigin(origins = "*")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable Long id) {
        List<MessageDto> messages = service.getAllConvertedMessages(id);
        return ResponseEntity.ok(messages);

    }

    @PostMapping("chat/alivechat")
    @CrossOrigin(origins = "*")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public ResponseEntity<Long> getAliveChat() {
        Long chatId = service.getAliveChat();
        return ResponseEntity.ok(chatId);
    }
}
