package edu.vwcc.ITP246Final.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.vwcc.ITP246Final.entities.Mood;
import edu.vwcc.ITP246Final.entities.User;

/*
 * Gets a mood record for the user. Also checks that user has a mood entry on a specified date
 */

public interface MoodRepository extends JpaRepository<Mood, Long> {

	// Finds a mood entry between two specified DateTime values.
	Optional<Mood> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);


	// Find all entries for the user sorted by timestamp
	List<Mood> findAllByUserOrderByTimestampAsc(User user);

}