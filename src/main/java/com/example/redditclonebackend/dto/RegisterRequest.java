package com.example.redditclonebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 Our new user register request class.
  The API call should contain the request body which is of type RegisterRequest.
  with this class we will be transferring the user registration details like
   username, password and email in a POST call.
  This request can be made in JSON for example:
  {
  "username":"user1",
  "email":"example1@example.com",
  "password":"123123"
  }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String username;
	private String email;
	private String password;
}