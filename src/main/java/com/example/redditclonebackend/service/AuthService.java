package com.example.redditclonebackend.service;


import com.example.redditclonebackend.config.AppConfig;
import com.example.redditclonebackend.dto.AuthenticationResponse;
import com.example.redditclonebackend.dto.LoginRequest;
import com.example.redditclonebackend.dto.RefreshTokenRequest;
import com.example.redditclonebackend.model.NotificationEmail;
import com.example.redditclonebackend.dto.RegisterRequest;
import com.example.redditclonebackend.model.User;
import com.example.redditclonebackend.model.VerificationToken;
import com.example.redditclonebackend.exception.SpringRedditException;
import com.example.redditclonebackend.repository.UserRepository;
import com.example.redditclonebackend.repository.VerificationTokenRepository;
import com.example.redditclonebackend.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static java.time.Instant.now;

/*
 This class is responsible for creating the User object and storing it in the database.
 A Service notation means that this class will be registered as a bean in the context.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

	// we will need our user repository to access users, and encode the user password
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;

	/*
	This will allow the user to log in after they verify their email.
	We will generate a verification token, right after we save the user
	to the database and send that token as part of the verification email.
	Once the user is verified, then we enable the user to login to our application.
	 */
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	private final RefreshTokenService refreshTokenService;
	private final AppConfig appConfig;


	/*
	The sign-up process is a transaction between this service and userRepository.
	We are mapping the RegisterRequest object to the User object and when setting
	the password, we are calling the encodePassword() method.
	This method is using the BCryptPasswordEncoder to encode our password.
	After that, we save the user into the database.
	 */
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setCreated(now());
		user.setEnabled(false);
		userRepository.save(user);
		log.info("User Registered Successfully, Sending Authentication Email");
		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate your Account",
				user.getEmail(), "Thank you for signing up to Spring Reddit, " +
				"please click on the below url to activate your account : " +
				appConfig.getAppUrl() + "/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString(); // creating a random id
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	// method for encoding the password using the password encoder found in our config.SecurityConfig class.
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	// we pull the token from the reopsitory, if the value is present then fetchUserAndEnable, if there is no value,
	// return SpringRedditException
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
		fetchUserAndEnable(verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token")));
	}

	// getting the user and enabling him in the system.
	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
		user.setEnabled(true);
		userRepository.save(user);
	}

	// our login method, returns an AuthenticationResponse DTO and accepts a LoginRequest DTO
	// this method authenticates the user and generates a JWT token, returns the DTO with the token
	// and the username.
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.username(loginRequest.getUsername())
				.build();
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
				getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	}


	public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenRequest.getRefreshToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.username(refreshTokenRequest.getUsername())
				.build();
	}

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}


}