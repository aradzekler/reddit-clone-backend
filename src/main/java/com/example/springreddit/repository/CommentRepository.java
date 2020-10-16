package com.example.springreddit.repository;

import com.example.springreddit.model.Comment;
import com.example.springreddit.model.Post;
import com.example.springreddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPost(Post post);
	List<Comment> findAllByUser(User user);
}