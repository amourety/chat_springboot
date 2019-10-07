package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.models.User;
import ru.itis.services.ChatService;

@Controller
public class    ChatController {

    @Autowired
    ChatService chatService;


    @GetMapping("/chat")
    @PreAuthorize("isAuthenticated()")
    public String getChatPage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user_username", user.getUsername());
        model.addAttribute("messages", chatService.getAllConvertedMessages());
        return "chat";
    }
}
