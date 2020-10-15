package com.example.springreddit.model;

import com.example.springreddit.exception.SpringRedditException;

import java.util.Arrays;

/*
	An Enum for storing if a vote is an Upvote (positive vote) or a Downvote.

 */
public enum VoteType {
	UPVOTE(1), DOWNVOTE(-1),
	;

	private int direction;

	VoteType(int direction) {
	}

	public static VoteType lookup(Integer direction) {
		return Arrays.stream(VoteType.values())
				.filter(value -> value.getDirection().equals(direction))
				.findAny()
				.orElseThrow(() -> new SpringRedditException("Vote not found"));
	}

	public Integer getDirection() {
		return direction;
	}
}
