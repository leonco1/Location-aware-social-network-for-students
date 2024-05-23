package mk.ukim.finki.wp.locationawareapp.web.controller;

import org.springframework.ui.Model;

import mk.ukim.finki.wp.locationawareapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/home","/"})
public class HomeController {
    @GetMapping
    public String getHomePage(Model model)
    {
        return "admin-page.html";
    }
}
