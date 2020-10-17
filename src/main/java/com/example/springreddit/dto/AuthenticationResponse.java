package com.example.springreddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/*
the response for the controller's authentication response, we will have
to authenticate before logging in, after a logging request is made and the
user authenticated he gets back an authenticated token
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
	private String authenticationToken;
	private String refreshToken;
	private Instant expiresAt;
	private String username;
}