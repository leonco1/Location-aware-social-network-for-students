package mk.ukim.finki.wp.locationawareapp.service;

import mk.ukim.finki.wp.locationawareapp.config.WebSocketConfig;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class UserSessionRegistry {

    private final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();

    public Integer getSize()
    {
        return userSessions.size();
    }
    public void addUser(String sessionId, String username) {
        userSessions.put(sessionId, username);
    }

    public Set<String>getAllSessions()
    {
        return userSessions.keySet();
    }
    public Set<String> getAllUsers() {
        return new HashSet<>(userSessions.values());}


}
