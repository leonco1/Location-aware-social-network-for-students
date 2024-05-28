package mk.ukim.finki.wp.locationawareapp.web.controller;

import mk.ukim.finki.wp.locationawareapp.service.UserService;
import mk.ukim.finki.wp.locationawareapp.service.WifiService;
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
    private final WifiService wifiService;

    public AdminController(UserService userService,WifiService wifiService) {
        this.userService = userService;
        this.wifiService=wifiService;
    }

    @GetMapping
    public String getHomePage(Model model)
    {
        return "../templates/admin-page";
    }
    @GetMapping("/send_signals")
    public String SendRequests(Model model) throws IOException {
        wifiService.SendMessage();
        return "../templates/index-chat-page";
    }
}
