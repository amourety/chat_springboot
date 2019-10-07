package ru.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.models.User;
import ru.itis.security.UsersDetailsImpl;
import ru.itis.services.ChatService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {

    private final Map<Long, List<Message>> newMessagesCounter = new HashMap<>();

    @Autowired
    private ChatService service;

    @PostMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Message m = Message.builder()
                .text(message.getText())
                .userId(user.getId())
                .build();
        if (!newMessagesCounter.containsKey(user.getId())) {
            newMessagesCounter.put(user.getId(), new ArrayList<>());
        }
        if (message.getUsername() == null) {
            service.addMessage(m);
        }
        for (List<Message> messages : newMessagesCounter.values()) {
            synchronized (messages) {
                messages.add(m);
                messages.notifyAll();
            }
        }
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDto>> getMessagesForPage() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        synchronized (newMessagesCounter.get(user.getId())) {
            if (newMessagesCounter.get(user.getId()).isEmpty()) {
                newMessagesCounter.get(user.getId()).wait();
            }
            newMessagesCounter.get(user.getId()).clear();
            List<MessageDto> response = service.getAllConvertedMessages();
            return ResponseEntity.ok(response);
        }
    }
}
