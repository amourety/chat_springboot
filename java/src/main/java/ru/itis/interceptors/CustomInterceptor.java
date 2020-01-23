package ru.itis.interceptors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import ru.itis.services.ChatService;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@Setter
public class CustomInterceptor extends ChannelInterceptorAdapter {

    @Autowired
    ChatService service;

    private HashMap<String, String> usernameWithSession = new HashMap<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert accessor != null;
        switch (Objects.requireNonNull(accessor.getCommand())) {
            case CONNECT:
                usernameWithSession.put(accessor.getSessionId(), accessor.getFirstNativeHeader("USERNAME"));
                break;
            case SUBSCRIBE:
                System.out.println("SUBCRIBE!");
                break;
            case DISCONNECT:
                //find by sessionId username from hashMap
                String username = usernameWithSession.get(accessor.getSessionId());

                //find by username chatId
                Long chatId = service.findRoom(username).getChatId();

                //reset user location
                service.updateUserChat(username, null);
                try {
                    channel.send(constructMessage(accessor.getSessionId(), chatId));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;

        }
        System.out.println(message);
        return message;
    }
    private Message<?> constructMessage(String sessionId, Long chatId) throws JsonProcessingException {
        byte[] bytes = new ObjectMapper().writeValueAsBytes(chatId);
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setSessionId(sessionId);
        headers.setDestination("/messageStomp/notify");
        headers.setSessionAttributes(new ConcurrentHashMap<>());
        headers.addNativeHeader("CHAT_ID", String.valueOf(chatId));
        return MessageBuilder.createMessage(bytes, headers.getMessageHeaders());
    }
}