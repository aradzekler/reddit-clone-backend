package com.example.redditclonebackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/*
	Vote entity, containing Lombok annotations for less mess!
	The Vote class handles vote data.

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long voteId;
	private VoteType voteType;

	@NotNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "postId")
	private Post post;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;
}