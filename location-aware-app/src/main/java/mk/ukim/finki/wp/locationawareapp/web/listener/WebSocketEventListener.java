package mk.ukim.finki.wp.locationawareapp.web.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.locationawareapp.model.ChatMessage;
import mk.ukim.finki.wp.locationawareapp.model.Enum.MessageType;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageSendingOperations;

    //TODO-- IMPLEMENT LATER
    @EventListener
    public void HandleWebSocketDisconnectListener(SessionDisconnectEvent event)
    {
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String username=(String) headerAccessor.getSessionAttributes().get("username");

        if(username!=null)
        {
            log.info("User disconnected: {}",username);
            ChatMessage chatMessage=ChatMessage.builder().messageType(MessageType.LEAVE).
                    sender(username).build();
            messageSendingOperations.convertAndSend("/topic/public",chatMessage);
        }
    }
}
