package com.example.springreddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// The DTO for a response.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	private Long id;
	private String postName;
	private String url;
	private String description;
	private String userName;
	private String subredditName;
	private Integer voteCount;
	private Integer commentCount;
	private String duration;
}