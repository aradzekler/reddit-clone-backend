package com.example.springreddit.repositories;

import com.example.springreddit.domain.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
	Same principle as UserRepository
 */
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
	Optional<Subreddit> findByName(String subredditName);
}