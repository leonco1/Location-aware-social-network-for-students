package mk.ukim.finki.wp.locationawareapp.service.impl;

import mk.ukim.finki.wp.locationawareapp.model.Enum.Role;
import mk.ukim.finki.wp.locationawareapp.model.User;
import mk.ukim.finki.wp.locationawareapp.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wp.locationawareapp.repository.UserRepository;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User createAdmin(String username, String password, Role role) {
        User user = new User(username,password, role);
        return userRepository.save(user);
    }

    @Override
    public void createUser(String username, Role role) {
        User user =new User(username,role);
        userRepository.save(user);
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(UsernameAlreadyExistsException::new);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(user.getRole())
        );
    }
}
