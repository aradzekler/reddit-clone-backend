package com.example.springreddit.repositories;


import com.example.springreddit.domain.Comment;
import com.example.springreddit.domain.Post;
import com.example.springreddit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
	Same principle as UserRepository
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPost(Post post);

	List<Comment> findAllByUser(User user);
}