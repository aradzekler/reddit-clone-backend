package com.example.springreddit.repositories;

import com.example.springreddit.domain.Post;
import com.example.springreddit.domain.Subreddit;
import com.example.springreddit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
	Same principle as UserRepository
 */
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);
}