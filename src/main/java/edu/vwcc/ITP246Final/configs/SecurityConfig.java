package edu.vwcc.ITP246Final.configs;

import edu.vwcc.ITP246Final.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * This security configuration class handles authentication, authorization and password encoding for the 
 * users table in the h2 database. Endpoints here specify user access and add constraits based on 
 * log in status and account registration.
 */

// Denotes the bean for this class as a configuration in the spring application context
@Configuration
// Enables web security support from Spring Security
@EnableWebSecurity
public class SecurityConfig {

	// Allows the application to load user specific data
	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	// Configures how request are stored
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests // Configures authorization for HTTP request
                // Allows access to the registration page and the h2 console without being
                // logged in
        		.requestMatchers("/register", "/login").permitAll()
        		.requestMatchers("/h2-console/**").access((auth, context) -> {
        		    var authentication = auth.get();
        		    return authentication != null && authentication.getName().equals("David")
        		        ? new org.springframework.security.authorization.AuthorizationDecision(true)
        		        : new org.springframework.security.authorization.AuthorizationDecision(false);
        		})
                // All other pages require authentication
                .anyRequest().authenticated())
                // Redirect to homepage after login
		        .formLogin(form -> form
		        	    .loginPage("/login")               // Use the custom login page
		        	    .loginProcessingUrl("/login")      // Endpoint that Spring Security intercepts
		        	    .defaultSuccessUrl("/", true)      // Where to go after successful login
		        	    .permitAll()
		        	)
                // Enables log out for authenticated users
                .logout(logout -> logout.permitAll());

		// Enable H2 console access
		http.csrf().disable();
		http.headers().frameOptions().disable();

		// Build and returns the security filter change
		return http.build();
	}

	// Creates a password encoder bean to be used when registering a new user
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder()).and().build();
	}

}
