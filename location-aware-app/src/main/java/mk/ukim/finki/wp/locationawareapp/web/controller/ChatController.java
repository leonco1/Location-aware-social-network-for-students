package mk.ukim.finki.wp.locationawareapp.web.controller;



import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.locationawareapp.model.ChatMessage;
import mk.ukim.finki.wp.locationawareapp.model.Enum.Role;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
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
    private final UserService userService;

    public ChatController(UserSessionRegistry userSessionRegistry, UserService userService) {
        this.userSessionRegistry = userSessionRegistry;
        this.userService = userService;
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
        if (userSessionRegistry.getAllUsers().isEmpty()) {
            userService.createUser(username, Role.ROLE_ADMIN);
        } else {
            userService.createUser(username, Role.ROLE_USER);
        }

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        userSessionRegistry.addUser(headerAccessor.getSessionId(), username);
        log.info("User connected: {}", username);
        log.info("User role:{}",userService.findByUsername(username).get().getRole());
        return chatMessage;
    }
}
