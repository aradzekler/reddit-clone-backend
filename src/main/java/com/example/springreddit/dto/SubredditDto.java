package com.example.springreddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
	DTO file that contains metadata about the Subreddit.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDto {
	private Long id;
	private String name;
	private String description;
	private Integer numberOfPosts;
}