package mk.ukim.finki.wp.locationawareapp.service;

import mk.ukim.finki.wp.locationawareapp.model.Enum.Role;
import mk.ukim.finki.wp.locationawareapp.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User createAdmin(String username, String password, Role role);
    User createUser(String username);


}
