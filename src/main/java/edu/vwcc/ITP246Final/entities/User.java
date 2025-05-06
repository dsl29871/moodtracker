package edu.vwcc.ITP246Final.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
	// Marks column as primary
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// Ensure that the username is not duplicated
	@Column(unique = true)
	private String username;
	// Store hashed password
	private String password;

	// Default constructors
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
