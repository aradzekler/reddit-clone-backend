package com.example.springreddit.repository;

import com.example.springreddit.model.Post;
import com.example.springreddit.model.Subreddit;
import com.example.springreddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
	Same principle as UserRepository
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);
}