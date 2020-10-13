package com.example.springreddit.repository;

import com.example.springreddit.model.Post;
import com.example.springreddit.model.User;
import com.example.springreddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
	Same principle as UserRepository
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}