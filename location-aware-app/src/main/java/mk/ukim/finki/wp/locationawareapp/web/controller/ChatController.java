package mk.ukim.finki.wp.locationawareapp.web.controller;



import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.locationawareapp.model.ChatMessage;
import mk.ukim.finki.wp.locationawareapp.model.Enum.Role;
import mk.ukim.finki.wp.locationawareapp.model.User;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
import mk.ukim.finki.wp.locationawareapp.service.UserSessionRegistry;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Set;

@Controller
@Slf4j
public class ChatController {
    private final UserSessionRegistry userSessionRegistry;
    private final UserService userService;

    public ChatController (UserSessionRegistry userSessionRegistry, UserService userService) {
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
            if(userService.findByUsername(username).isPresent())
            {
                if(userService.findByUsername(username).get().getRole().equals(Role.ROLE_ADMIN))
                    userService.createUser(username,Role.ROLE_ADMIN);
                else userService.createUser(username,Role.ROLE_USER);

            }
            else
                userService.createUser(username, Role.ROLE_USER);
        }
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        userSessionRegistry.addUser(headerAccessor.getSessionId(), username);
        chatMessage.setRole(userService.findByUsername(username).get().getRole());
        log.info("User connected: {}", username);
        log.info("User role:{}",userService.findByUsername(username).get().getRole());
        return chatMessage;
    }
    @MessageMapping("/change")
    @SendTo("/topic/changes")
    public String broadcastChange(String message)
    {
        return message;
    }



}
