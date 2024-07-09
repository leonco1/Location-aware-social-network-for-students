package mk.ukim.finki.wp.locationawareapp.web.listener;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.locationawareapp.model.ChatMessage;
import mk.ukim.finki.wp.locationawareapp.model.Enum.MessageType;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
import mk.ukim.finki.wp.locationawareapp.service.UserSessionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import javax.naming.Context;
import javax.swing.*;
import java.io.IOException;

import static mk.ukim.finki.wp.locationawareapp.LocationAwareAppApplication.exitApp;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final UserService userService;

    private final SimpMessageSendingOperations messagingTemplate;

    private final UserSessionRegistry userSessionRegistry;
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws IOException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
            if (username != null) {
                if ((userService.findByUsername(username).get().getRole().getAuthority().equals("ROLE_ADMIN"))) {
                    var chatMessage = ChatMessage.builder()
                            .type(MessageType.LEAVE)
                            .sender(username)
                            .content("Admin has left,chat will now close")
                            .build();
                    messagingTemplate.convertAndSend("/topic/public", chatMessage);
                    //exitApp();
                } else {
                    log.info("user disconnected: {}", username);
                    var chatMessage = ChatMessage.builder()
                            .type(MessageType.LEAVE)
                            .sender(username)
                            .build();
                    messagingTemplate.convertAndSend("/topic/public", chatMessage);
                }
            }
        }

    }

