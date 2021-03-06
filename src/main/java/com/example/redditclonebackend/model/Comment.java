package com.example.redditclonebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


/*
	Comment entity, containing Lombok annotations for less mess!
	The Comment class handles the comments data (Posts can have comments).

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="_comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotEmpty
	private String text;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "postId")
	private Post post;

	private Instant createdDate;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;
}