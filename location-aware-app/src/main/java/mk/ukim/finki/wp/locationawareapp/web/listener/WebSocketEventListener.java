package mk.ukim.finki.wp.locationawareapp.web.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.locationawareapp.model.ChatMessage;
import mk.ukim.finki.wp.locationawareapp.model.Enum.MessageType;
import mk.ukim.finki.wp.locationawareapp.service.UserSessionRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageSendingOperations;
    private final UserSessionRegistry userSessionRegistry;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            userSessionRegistry.addUser(sessionId, username);
            log.info("User connected: {}", username);
            messageSendingOperations.convertAndSend("/topic/activeUsers", userSessionRegistry.getAllUsers());
        }
    }

    @EventListener
    public void HandleWebSocketDisconnectListener(SessionDisconnectEvent event)
    {
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String username=(String) headerAccessor.getSessionAttributes().get("username");

        if(username!=null)
        {
            userSessionRegistry.removeUser(sessionId);
            log.info("User disconnected: {}",username);
            var chatMessage=ChatMessage.builder().
                    messageType(MessageType.LEAVE).
                    sender(username).build();
            messageSendingOperations.convertAndSend("/topic/public",chatMessage);
            messageSendingOperations.convertAndSend("/topic/activeUsers", userSessionRegistry.getAllUsers());
        }
    }
}
