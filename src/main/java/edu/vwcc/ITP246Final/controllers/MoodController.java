package edu.vwcc.ITP246Final.controllers;

import edu.vwcc.ITP246Final.entities.Mood;
import edu.vwcc.ITP246Final.entities.User;
import edu.vwcc.ITP246Final.repositories.MoodRepository;
import edu.vwcc.ITP246Final.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MoodController {
	// Repository for mood data
	private final MoodRepository moodRepo;
	// Repository for user data
	private final UserRepository userRepo;

	// Constructor injection of repositories
	public MoodController(MoodRepository moodRepo, UserRepository userRepo) {
		this.moodRepo = moodRepo;
		this.userRepo = userRepo;
	}

	// Displays the home page of the application where users can add and review data
	@GetMapping("/home")
	public String homePage(Model model, Authentication auth) {
		// Retrieve the logged in user
		String username = auth.getName();
		// Throw exception if the user is not found
		User user = userRepo.findByUsername(username).orElseThrow();
		// All of the previously entered mood recording are displayed ordered by
		// timestamp descending
		List<Mood> moods = moodRepo.findAllByUserOrderByTimestampAsc(user);
		// Add the list of mood records to the page
		model.addAttribute("moods", moods);
		return "home";
	}

	// Processes the form the record a new log for a daily mood
	@PostMapping("/add")
	public String addMood(@RequestParam String moodLevel, @RequestParam String note,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate timestamp, Authentication auth) {

		// Get the logged-in user
		String username = auth.getName();
		User user = userRepo.findByUsername(username).orElseThrow();

		// Define start/end of selected day
		LocalDateTime start = timestamp.atStartOfDay();
		LocalDateTime end = timestamp.atTime(LocalTime.MAX);

		// Delete any existing mood for that user on that day
		moodRepo.findByUserAndTimestampBetween(user, start, end)
				.ifPresent(existing -> moodRepo.deleteById(existing.getId()));

		// Save the new entry
		Mood mood = new Mood(moodLevel, note);
		mood.setTimestamp(start);
		mood.setUser(user);
		moodRepo.save(mood);

		return "redirect:/";
	}

	// Deletes an entry based on the ID number so it can be replaced
	@PostMapping("/delete/{id}")
	public String deleteMood(@PathVariable Long id) {
		// Deletes entry by pass id value
		moodRepo.deleteById(id);
		return "redirect:/";
	}

}
