package net.datasa.web5.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String goHome() {
        return "home";
    }

    @GetMapping("/security")
    public String goSecurityTest() {
        return "member/security";
    }
}
