package ru.itis.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.LoginDto;
import ru.itis.dto.MessageDto;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserForm;
import ru.itis.models.Message;
import ru.itis.models.Token;
import ru.itis.models.User;
import ru.itis.repositories.MessageRepository;
import ru.itis.repositories.TokenRepository;
import ru.itis.repositories.UserRepository;
import ru.itis.security.UsersDetailsImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<MessageDto> getAllConvertedMessages(){
        List<Message> messages = getAllMessages();
        List<MessageDto> messageDto = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        for(Message m: messages){
            messageDto.add(MessageDto.builder()
                    .username(userRepository.find(m.getUserId()).getUsername())
                    .text(m.getText())
                    .time(dateFormat.format(m.getTime()))
                    .build());
        }
        Collections.reverse(messageDto);
        return messageDto;
    }
    public MessageDto convert(Message m){
        return MessageDto.builder()
                .username(userRepository.find(m.getUserId()).getUsername())
                .text(m.getText())
                .build();
    }

    public void addMessage(Message message) {
        messageRepository.save(message);
    }


    public void signUp(UserForm userForm) {
        User user = User.builder()
                .username(userForm.getUsername())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .build();
        userRepository.save(user);

    }
    public boolean login(LoginDto loginData) {
        User userCandidate = userRepository.findByUsername(loginData.getLogin());
        if (userCandidate != null) {
            if (passwordEncoder.matches(loginData.getPassword(), userCandidate.getPassword())) {
                return true;
            }
        } throw new BadCredentialsException("Incorrect login or password");

    }

    public TokenDto token(LoginDto loginData) {
        User userCandidate = userRepository.findByUsername(loginData.getLogin());

        if (userCandidate != null) {
            if (passwordEncoder.matches(loginData.getPassword(), userCandidate.getPassword())) {
                String value = UUID.randomUUID().toString();
                Token token = Token.builder()
                        .value(value)
                        .userId(userCandidate.getId())
                        .createdAt(LocalDateTime.now())
                        .expiredDateTime(LocalDateTime.now().plusSeconds(10000))
                        .build();
                tokenRepository.save(token);
                System.out.println("userCandidate:" + userCandidate.getUsername() + ": token : " + token.getValue());
                return TokenDto.from(value);
            }
        } throw new BadCredentialsException("Incorrect login or password");
    }
}
