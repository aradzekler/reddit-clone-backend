package com.example.springreddit.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
This is the base class and interface for our SecurityConfig class,
 it provides us the default security configurations,
 which we can override in our SecurityConfig and customize them.
 */
@EnableWebSecurity // main annotation which enables the Web Security module in our Project.
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//UserDetailsService is an interface, we have to provide an implementation
	// where it fetches the user information from our MySQL Database: UserDetailsServiceImpl class.
	private final UserDetailsService userDetailsService; // userDetailsService interface for checking user details.

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/auth/**") // allow all the requests which match the endpoint “/api/auth/**”
				.permitAll()
				.anyRequest()
				.authenticated();
	}

	// configured AuthenticationManagerBuilder to use the UserDetailsService interface to check for user information.
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
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