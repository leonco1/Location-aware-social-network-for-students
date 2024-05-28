package mk.ukim.finki.wp.locationawareapp.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.locationawareapp.model.Enum.Role;
import mk.ukim.finki.wp.locationawareapp.model.User;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class  DataHolder {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public DataHolder(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostConstruct
    public void initData()
    {
        String encodedPassword= passwordEncoder.encode("km");
        User admin=this.userService.createAdmin("kostadin.mishev",encodedPassword,Role.ROLE_ADMIN);
    }
}
