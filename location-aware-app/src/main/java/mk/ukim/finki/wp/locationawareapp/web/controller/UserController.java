package mk.ukim.finki.wp.locationawareapp.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.locationawareapp.model.User;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
import mk.ukim.finki.wp.locationawareapp.service.WifiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
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
}
