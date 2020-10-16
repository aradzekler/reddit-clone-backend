package com.example.springreddit.util.mapper;

import com.example.springreddit.dto.SubredditDto;
import com.example.springreddit.model.Post;
import com.example.springreddit.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/*
	The SubredditMapper interface uses @Mapper(componentModel=’spring’) annotation to
	specify that this interface is a Mapstruct Mapper and Spring should identify
	it as a component and should be able to inject it into other components like
	SubredditService.
 */
@Mapper(componentModel = "spring")
public interface SubredditMapper {

	/*
		This method responsible for Subreddit entity to DTO mapping. It contains only one
		@Mapping annotation for the target field numberOfPosts, in this case, we are
		mapping from List<Posts> to an Integer, this kind of mapping is not straight
		forward and we need to write our logic. We can do that by using the expression
		field and pass the method definition for mapPosts() which returns an Integer.
	 */
	@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);

	default Integer mapPosts(List<Post> numberOfPosts) {
		return numberOfPosts.size(); // the size of numberOfPosts ie total number of posts in a subreddit.
	}

	/*
		The @InheritInverseConfiguration makes this method an inverse version of the
		called method, now, a DTO taken as an input into a Subreddit.
	 */
	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit mapDtoToSubreddit(SubredditDto subreddit);
}