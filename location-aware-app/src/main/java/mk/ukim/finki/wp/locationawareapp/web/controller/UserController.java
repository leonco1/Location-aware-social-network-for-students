package mk.ukim.finki.wp.locationawareapp.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.locationawareapp.model.ChatMessage;
import mk.ukim.finki.wp.locationawareapp.model.Enum.MessageType;
import mk.ukim.finki.wp.locationawareapp.model.Enum.Role;
import mk.ukim.finki.wp.locationawareapp.model.User;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
import mk.ukim.finki.wp.locationawareapp.service.WifiService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static mk.ukim.finki.wp.locationawareapp.LocationAwareAppApplication.exitApp;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(HttpSession session, Model model)
    {
        Boolean revealMainPage = (Boolean) session.getAttribute("revealMainPage");
        if (revealMainPage != null && revealMainPage) {
            model.addAttribute("message", "Welcome to the main page!");
            return "index-chat-page"; // Return the view for the main page
        } else {
            return "access-denied"; // Return a view indicating access is denied
        }
    }
    @PostMapping("/exit")
    public String exit(@RequestParam String role, @RequestParam String username) {
        if (role.equals(Role.ROLE_USER.getAuthority())) {
            userService.removeUser(username);
            return "index-chat-page";
        } else if (role.equals(Role.ROLE_ADMIN.getAuthority())) {
            userService.removeUser(username);
            exitApp();
        }
        return "index-chat-page";
    }
    }

