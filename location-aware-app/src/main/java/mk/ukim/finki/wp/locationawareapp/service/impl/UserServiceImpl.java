package mk.ukim.finki.wp.locationawareapp.service.impl;

import mk.ukim.finki.wp.locationawareapp.model.Role;
import mk.ukim.finki.wp.locationawareapp.model.User;
import mk.ukim.finki.wp.locationawareapp.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wp.locationawareapp.repository.UserRepository;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User create(String username, String password, Role role) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, Role.ROLE_USER);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(String username, Role role) {
        return null;
    }

    @Override
    public User register(String username) {
        if(findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException();
        return this.userRepository.save(new User(username,Role.ROLE_USER));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
