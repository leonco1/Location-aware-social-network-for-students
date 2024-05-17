package mk.ukim.finki.wp.locationawareapp.service;

import mk.ukim.finki.wp.locationawareapp.model.Role;
import mk.ukim.finki.wp.locationawareapp.model.User;

import java.util.Optional;

public interface UserService {
    User register(String username);
    Optional<User> findByUsername(String username);
    User create(String username, Role role);



}
