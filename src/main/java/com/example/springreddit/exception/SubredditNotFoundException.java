package com.example.springreddit.exception;

public class SubredditNotFoundException extends RuntimeException {
	public SubredditNotFoundException(String message) {
		super(message);
	}
}
