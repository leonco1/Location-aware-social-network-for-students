package mk.ukim.finki.wp.locationawareapp.service;

import mk.ukim.finki.wp.locationawareapp.model.Role;
import mk.ukim.finki.wp.locationawareapp.model.User;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User createAdmin(String username, String password, Role role);
    User createUser(String username);
    Optional<String> getWifi() throws IOException;
    void SendMessage() throws IOException;



}
