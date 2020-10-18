package com.example.redditclonebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
	Another DTO object for the login request itself, contains a username
	and a password wrapped inside a POST call to the server, the server
	authenticates the user and returns a token using AuthenticationResponse DTO.
	The login endpoint passes the LoginRequest object to the login()
	method of the AuthService class after the POST call has been made.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	private String username;
	private String password;
}