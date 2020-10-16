package com.example.springreddit.controller;

import com.example.springreddit.dto.CommentDto;
import com.example.springreddit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
		commentService.save(commentDto);
		return new ResponseEntity<>(CREATED);
	}

	@GetMapping("by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
		return status(OK)
				.body(commentService.getAllCommentsForPostService(postId));
	}

	@GetMapping("by-user/{userName}")
	public ResponseEntity<List<CommentDto>> getAllCommentsByUser(@PathVariable String userName) {
		return status(OK)
				.body(commentService.getAllCommentsForUserService(userName));
	}
}
