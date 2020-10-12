package com.example.springreddit.repositories;

import com.example.springreddit.domain.Post;
import com.example.springreddit.domain.User;
import com.example.springreddit.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
	Same principle as UserRepository
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}