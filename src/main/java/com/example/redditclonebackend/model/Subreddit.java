package com.example.redditclonebackend.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/*
	Subreddit entity, containing Lombok annotations for less mess!
	The Subreddit class handles the subreddit data itself. includes all the posts.
	(Subreddit == a sub-forum)

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="_subreddit")
public class Subreddit {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotBlank(message = "Community name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;

	@OneToMany(fetch = LAZY)
	private List<Post> posts;
	private Instant createdDate;

	@ManyToOne(fetch = LAZY)
	private User user;
}