package ru.itis.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.MessageDto;
import ru.itis.models.Location;
import ru.itis.models.Message;
import ru.itis.repositories.ChatRepository;
import ru.itis.repositories.MessageRepository;
import ru.itis.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<MessageDto> getAllConvertedMessages(Long id) {
        List<MessageDto> messageDto = findMessageAsString(id);
        Collections.reverse(messageDto);
        return messageDto;
    }


    public void addMessage(MessageDto message) {
        message.addTime();
        messageRepository.saveWS(message);
    }

    public List<MessageDto> findMessageAsString(Long id) {
        return messageRepository.findMessageAsString(id);
    }


    public void saveUserToChat(String username, Long chatId) {
        chatRepository.save(Location.builder().username(username).chatId(chatId).build());
    }

    public boolean isUserWithoutChatRoom(String username) {
        Location location = chatRepository.findByUsername((username));
        return location != null && location.getChatId() != null;
    }

    public void updateUserChat(String username, Long chatId) {
        chatRepository.update(Location.builder().username(username).chatId(chatId).build());
    }

    public Location findRoom(String username) {
        return chatRepository.findByUsername(username);
    }

    public Long getAliveChat() {
        Location location = chatRepository.findAliveChat();
        if (location == null) {
            return 1L;
        } else {
            return location.getChatId();
        }
    }

    public List<String> getChatRoomUserNames(String id) {
        List<Location> locations = getLocationByChatId(String.valueOf(id));
        List<String> usernameList = new ArrayList<>();
        for (Location location : locations) {
            System.out.println(location.getUsername());
            usernameList.add(location.getUsername());
        }
        return usernameList;
    }

    private List<Location> getLocationByChatId(String id) {
        return chatRepository.findAllByChat(Long.parseLong(id));
    }
}
