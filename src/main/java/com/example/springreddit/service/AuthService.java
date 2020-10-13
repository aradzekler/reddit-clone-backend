package com.example.springreddit.service;


import com.example.springreddit.model.NotificationEmail;
import com.example.springreddit.dto.RegisterRequest;
import com.example.springreddit.model.User;
import com.example.springreddit.model.VerificationToken;
import com.example.springreddit.exception.SpringRedditException;
import com.example.springreddit.repository.UserRepository;
import com.example.springreddit.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.example.springreddit.util.Constants.ACTIVATION_EMAIL;
import static java.time.Instant.now;

/*
 This class is responsible for creating the User object and storing it in the database.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

	// we will need our user repository to access users, and encode the user password
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/*
	This will allow the user to log in after they verify their email.
	We will generate a verification token, right after we save the user
	to the database and send that token as part of the verification email.
	Once the user is verified, then we enable the user to login to our application.
	 */
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

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
		user.setEnabled(false); // We are setting the enabled flag as false, as we want to disable the user
								// after registration, and only enable the user after verifying the user’s email address.
		userRepository.save(user); // transaction end.
		String token = generateVerificationToken(user);
		String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
				+ ACTIVATION_EMAIL + "/" + token);
		mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
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
		verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));
		fetchUserAndEnable(verificationTokenOptional.get());
	}

	// getting the user and enabling him in the system.
	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
		user.setEnabled(true);
		userRepository.save(user);
	}
}