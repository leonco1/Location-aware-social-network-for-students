package mk.ukim.finki.wp.locationawareapp.service;

import mk.ukim.finki.wp.locationawareapp.config.WebSocketConfig;
import mk.ukim.finki.wp.locationawareapp.model.User;
import mk.ukim.finki.wp.locationawareapp.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class UserSessionRegistry {
    private final UserRepository userRepository;

    public UserSessionRegistry(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();

    public Integer getSize()
    {
        return userSessions.size();
    }
    public void addUser(String sessionId, String username) {
        userSessions.put(sessionId, username);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
