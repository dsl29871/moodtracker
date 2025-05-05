package edu.vwcc.ITP246Final.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.vwcc.ITP246Final.entities.User;

@Controller
public class HomeController {

	
	// Redirect to /register when no logged in user
    @GetMapping("/")
    public String rootRedirect(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/home";
        }
        return "redirect:/register";
    }
}