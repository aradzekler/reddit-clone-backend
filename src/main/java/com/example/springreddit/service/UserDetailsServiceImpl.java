package com.example.springreddit.service;


import com.example.springreddit.model.User;
import com.example.springreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

/*
The class overrides the method loadUserByUsername() which is used by Spring Security to
 fetch the user details. Inside the method, we are querying the UserRepository and
  fetching those details and wrapping them in another User object which implements the UserDetails interface.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		User user = userOptional
				.orElseThrow(() -> new UsernameNotFoundException("No user " +
						"Found with username : " + username));

		return new org.springframework.security
				.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true,
				true, getAuthorities("USER"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return singletonList(new SimpleGrantedAuthority(role));
	}
}