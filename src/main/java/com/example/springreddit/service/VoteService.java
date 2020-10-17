package com.example.springreddit.service;

import com.example.springreddit.dto.VoteDto;
import com.example.springreddit.exception.PostNotFoundException;
import com.example.springreddit.exception.SpringRedditException;
import com.example.springreddit.model.Post;
import com.example.springreddit.model.Vote;
import com.example.springreddit.repository.PostRepository;
import com.example.springreddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.springreddit.model.VoteType.UPVOTE;


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