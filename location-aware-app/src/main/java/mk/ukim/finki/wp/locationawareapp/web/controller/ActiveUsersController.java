package mk.ukim.finki.wp.locationawareapp.web.controller;

import mk.ukim.finki.wp.locationawareapp.service.UserSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ActiveUsersController {

    @Autowired
    private UserSessionRegistry userSessionRegistry;

    @GetMapping("/api/connected-users")
    public Set<String> getAllConnectedUsers() {
        return userSessionRegistry.getAllUsers();
    }
}
