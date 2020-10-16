package com.example.springreddit.service;

import com.example.springreddit.dto.CommentDto;
import com.example.springreddit.exception.PostNotFoundException;
import com.example.springreddit.util.mapper.CommentMapper;
import com.example.springreddit.model.Comment;
import com.example.springreddit.model.NotificationEmail;
import com.example.springreddit.model.Post;
import com.example.springreddit.model.User;
import com.example.springreddit.repository.CommentRepository;
import com.example.springreddit.repository.PostRepository;
import com.example.springreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;



@Service
@AllArgsConstructor
public class CommentService {
	private static final String POST_URL = "";
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

	public void save(CommentDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);

		String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
	}

	public List<CommentDto> getAllCommentsForPostService(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		return commentRepository.findByPost(post)
				.stream()
				.map(commentMapper::mapToDto).collect(toList());
	}

	public List<CommentDto> getAllCommentsForUserService(String userName) {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException(userName));
		return commentRepository.findAllByUser(user)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(toList());
	}
}