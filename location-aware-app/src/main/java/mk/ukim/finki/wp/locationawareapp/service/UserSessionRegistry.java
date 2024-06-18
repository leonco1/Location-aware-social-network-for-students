package mk.ukim.finki.wp.locationawareapp.service;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserSessionRegistry {
    private final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();

    public void addUser(String sessionId, String username) {
        userSessions.put(sessionId, username);
    }

    public void removeUser(String sessionId) {
        userSessions.remove(sessionId);
    }

    public Set<String> getAllUsers() {
        return Set.copyOf(userSessions.values());
    }
}
