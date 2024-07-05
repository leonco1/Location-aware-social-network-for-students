package mk.ukim.finki.wp.locationawareapp.service;

import mk.ukim.finki.wp.locationawareapp.config.WebSocketConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class UserSessionRegistry {
    private final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, WebSocketSession>webSocketSessions=new ConcurrentHashMap<>();
    public void addUser(String sessionId, String username) {
        userSessions.put(sessionId, username);
    }

    public void removeUser(String sessionId) {
        userSessions.remove(sessionId);
    }

    public Set<String> getAllUsers() {
        return new HashSet<>(userSessions.values());}

    public Set<String>getAllsessionIds()
    {
        return this.userSessions.keySet();
    }
}
