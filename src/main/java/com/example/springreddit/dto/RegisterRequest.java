package com.example.springreddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 Our new user register request class.
  The API call should contain the request body which is of type RegisterRequest.
  with this class we will be transferring the user details like username, password and email
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String username;
	private String email;
	private String password;
}