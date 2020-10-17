package com.example.springreddit.service;

import com.example.springreddit.exception.SpringRedditException;
import com.example.springreddit.model.RefreshToken;
import com.example.springreddit.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/*
	When the client first authenticates, the server provides an additional
	token called a Refresh Token(stored inside our database) additional to
	the short-lived JWT. When our JWT is expired or about to be expired, we
	will use the refresh token to request a new JWT from the server.
	In this way, we can keep on rotating the token until the user decides to
	logout from the application. Once the user logs out, we will also delete
	the refresh token from the database. This leaves us with a very short
	window where the user logs out and the token is still valid.
 */
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	/*
	creates a random 128 bit UUID String and we are using that as our token.
	We are setting the createdDate as Instant.now(). And then we are saving the token to our MySQL Database.
	 */
	RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedDate(Instant.now());

		return refreshTokenRepository.save(refreshToken);
	}

	// queries the DB with the given token. If the token is not found it throws an Exception.
	void validateRefreshToken(String token) {
		refreshTokenRepository.findByToken(token)
				.orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
	}

	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteByToken(token);
	}
}