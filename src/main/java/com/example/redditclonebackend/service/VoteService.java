package com.example.redditclonebackend.service;

import com.example.redditclonebackend.dto.VoteDto;
import com.example.redditclonebackend.exception.PostNotFoundException;
import com.example.redditclonebackend.exception.SpringRedditException;
import com.example.redditclonebackend.model.Post;
import com.example.redditclonebackend.model.Vote;
import com.example.redditclonebackend.repository.PostRepository;
import com.example.redditclonebackend.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.redditclonebackend.model.VoteType.UPVOTE;


@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;

	@Transactional
	public void vote(VoteDto voteDto) { // retrieving the recent Vote submitted by the currently logged-in user for the given Post using findTopByPostAndUserOrderByVoteIdDesc()
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() &&
				voteByPostAndUser.get().getVoteType()
						.equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already "
					+ voteDto.getVoteType() + "d this post.");
		}
		if (UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}
}