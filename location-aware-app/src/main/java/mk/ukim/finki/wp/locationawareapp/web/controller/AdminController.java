package mk.ukim.finki.wp.locationawareapp.web.controller;

import mk.ukim.finki.wp.locationawareapp.service.UserService;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping({"/admin","/"})
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Model model)
    {
        return "admin-page";
    }
    @GetMapping("/send_signals")
    public String SendRequests(Model model) throws IOException {
        userService.SendMessage();
        return "index-chat-page";
    }
}
