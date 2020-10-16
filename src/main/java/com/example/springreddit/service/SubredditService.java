package com.example.springreddit.service;


import com.example.springreddit.dto.SubredditDto;
import com.example.springreddit.exception.SpringRedditException;
import com.example.springreddit.model.Subreddit;
import com.example.springreddit.repository.SubredditRepository;
import com.example.springreddit.util.mapper.SubredditMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final SubredditMapper subredditMapper; // Our MapStruct object which maps subreddits to DTO's.

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
				.stream()
				.map(subredditMapper::mapSubredditToDto)
				.collect(toList());
	}

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(subreddit.getId());
		return subredditDto;
	}

	@Transactional(readOnly = true)
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("Subreddit not found with id -" + id));
		return subredditMapper.mapSubredditToDto(subreddit);
	}


	/*
	We are mapping the Subreddit to a DTO file (as in converting details to
	our DTO object) and the opposite, but right now we have only 2 fields, but
	what about big objects with dozens of fields?
	we will use MapStruct for this.
	 */
}