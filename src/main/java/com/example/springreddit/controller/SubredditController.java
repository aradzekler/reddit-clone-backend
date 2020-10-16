package com.example.springreddit.controller;

import com.example.springreddit.dto.SubredditDto;
import com.example.springreddit.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*
	This is our controller for the Subreddit itself.
	It accepts REST calls that relates to the subreddit.
 */
@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

	private final SubredditService subredditService;

	@PostMapping
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(subredditService.save(subredditDto));
	}

	@GetMapping
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(subredditService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(subredditService.getSubreddit(id));
	}
}