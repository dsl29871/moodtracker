package edu.vwcc.ITP246Final.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
 * Represents a mood entry based on values in the database. 
 * Takes a user's mood level, notes, and the timestamp
 * when the mood was recorded
 */

@Entity
public class Mood {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Denotes a "many to one" relationship with the user
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String moodLevel;

	private String note;

	private LocalDateTime timestamp;

	public Mood() {
		this.timestamp = LocalDateTime.now();
	}

	public Mood(String moodLevel, String note) {
		this.moodLevel = moodLevel;
		this.note = note;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMoodLevel() {
		return moodLevel;
	}

	public void setMoodLevel(String moodLevel) {
		this.moodLevel = moodLevel;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
