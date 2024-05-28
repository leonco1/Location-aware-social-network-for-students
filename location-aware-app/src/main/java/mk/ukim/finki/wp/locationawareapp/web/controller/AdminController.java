package mk.ukim.finki.wp.locationawareapp.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
import mk.ukim.finki.wp.locationawareapp.service.WifiService;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping({"/admin"})
public class AdminController {


    @GetMapping
    public String getAdminPage(Model model)
    {
        return "admin-page";
    }
    @PostMapping("/send_signals")
    public String SendRequests(HttpSession httpSession) throws IOException {
        httpSession.setAttribute("revealMainPage",true);
        return "redirect:/";
    }
}
