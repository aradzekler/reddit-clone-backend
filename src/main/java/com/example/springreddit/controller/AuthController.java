package com.example.springreddit.controller;

import com.example.springreddit.dto.AuthenticationResponse;
import com.example.springreddit.dto.LoginRequest;
import com.example.springreddit.dto.RegisterRequest;
import com.example.springreddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

/*
 Our Authentication controller. it accepts an action such as POST or GET and transfers it to
 the handle of the relevant authService method and finally the relevant information to the view.
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	//TODO: the sign up should allow only unqiue registrations, get requests can only return a single value.
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity<>("Account Registration Successfully", OK);
	}

	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		authService.verifyAccount(token);
		return new ResponseEntity<>("Account Activated Successfully", OK);
	}

	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest); // The login endpoint passes the LoginRequest object to the login()
												// method of the AuthService class after the POST call has been made.
	}
}