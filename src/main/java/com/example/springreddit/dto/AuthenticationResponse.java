package com.example.springreddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
the response for the controller's authentication response, we will have
to authenticate before logging in, after a logging request is made and the
user authenticated he gets back an authenticated token
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {
	private String authenticationToken;
	private String username;
}