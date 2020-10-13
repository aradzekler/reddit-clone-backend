package com.example.springreddit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
This is the base class and interface for our SecurityConfig class,
 it provides us the default security configurations,
 which we can override in our SecurityConfig and customize them.
 */
@EnableWebSecurity // main annotation which enables the Web Security module in our Project.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/auth/**") // allow all the requests which match the endpoint “/api/auth/**”
				.permitAll()
				.anyRequest()
				.authenticated();
	}

	/*
	we will want to encode the user passwords.
	One of the best hashing algorithms for passwords is the Bcrypt Algorithm.
	We are using the BCryptPasswordEncoder class provided by Spring Security.
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}