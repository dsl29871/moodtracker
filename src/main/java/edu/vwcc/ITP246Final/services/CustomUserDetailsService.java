package edu.vwcc.ITP246Final.services;

import edu.vwcc.ITP246Final.entities.User;
import edu.vwcc.ITP246Final.repositories.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/*
 * Service class that implements the UserDetailsService from Spring Security.
 * This class authenticates users based on form data and the users table of the database
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

	// Repository for user
	private final UserRepository userRepo;

	// Constructor injection of user repo
	public CustomUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	// Overriden method loads a user for authentication
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepo.findByUsername(username)
				// Throws a user not found exception if that occurs
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		// Build and return Spring Security's User object with authentication info
		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()) // Set username
				.password(user.getPassword()) // Set hashed password
				.roles("USER") // Assign USER role to all users
				.build(); // Build the UserDetails object
	}
}
