package com.example.springreddit.domain;
/*
	An Enum for storing if a vote is an Upvote (positive vote) or a Downvote.

 */
public enum VoteType {
	UPVOTE(1), DOWNVOTE(-1),
	;

	VoteType(int direction) {
	}
}
