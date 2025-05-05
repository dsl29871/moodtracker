package edu.vwcc.ITP246Final.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vwcc.ITP246Final.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// Find a user by name
	Optional<User> findByUsername(String username);
}
