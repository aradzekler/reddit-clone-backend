package com.example.redditclonebackend.controller;

import com.example.redditclonebackend.dto.AuthenticationResponse;
import com.example.redditclonebackend.dto.LoginRequest;
import com.example.redditclonebackend.dto.RefreshTokenRequest;
import com.example.redditclonebackend.dto.RegisterRequest;
import com.example.redditclonebackend.service.AuthService;
import com.example.redditclonebackend.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
	private final RefreshTokenService refreshTokenService;

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

	@PostMapping("refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshToken(refreshTokenRequest);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
	}
}