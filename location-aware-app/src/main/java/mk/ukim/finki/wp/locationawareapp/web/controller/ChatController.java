package mk.ukim.finki.wp.locationawareapp.web.controller;



import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.locationawareapp.model.ChatMessage;
import mk.ukim.finki.wp.locationawareapp.service.UserSessionRegistry;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {
    private final UserSessionRegistry userSessionRegistry;

    public ChatController(UserSessionRegistry userSessionRegistry) {
        this.userSessionRegistry = userSessionRegistry;
    }


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username=chatMessage.getSender();
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        userSessionRegistry.addUser(headerAccessor.getSessionId(), username);
        log.info("User connected: {}", username);
        return chatMessage;
    }
}
