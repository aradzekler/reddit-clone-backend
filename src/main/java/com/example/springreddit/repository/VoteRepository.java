package com.example.springreddit.repository;

import com.example.springreddit.model.Post;
import com.example.springreddit.model.User;
import com.example.springreddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
	Same principle as UserRepository
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}