package edu.vwcc.ITP246Final.controllers;

import edu.vwcc.ITP246Final.entities.User;
import edu.vwcc.ITP246Final.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
 * Controller class manages registering a new user to the application
 * Allows the new user data to be saved to the users table of the database
 */

@Controller
public class AuthController {

	// Repository of user data
	private final UserRepository userRepo;
	// Password encoder for securing passwords in the db
	private final BCryptPasswordEncoder encoder;

	// Constructor injection of dependencies
	public AuthController(UserRepository userRepo, BCryptPasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	// Displays the user registration form
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		// Add a user object to the model
		model.addAttribute("user", new User());
		// Return to register view
		return "register";
	}

	// Process the user form data
	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user) {
		// Encrypt the password given from the register form
		user.setPassword(encoder.encode(user.getPassword()));
		// Save the user to the db
		userRepo.save(user);
		// Redirect user to the login page
		return "redirect:/login";
	}
}
