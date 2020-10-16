package com.example.springreddit.security;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
	After the client makes a REST call to our API and
	gets back a valid JWT token now he can start making
	requests for data. Those requests are intercepted by
	the JWTAuthenticationFilter, which is a custom component,
	this filter class validates JWT, and if the token is valid,
	the request is forwarded to the corresponding Controller.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
// The JwtAuthenticationFilter class extends the class OncePerRequestFilter,
// by extending this class we can implement our validation logic inside the doFilterInternal() method.

	@Autowired // Autowired allows us to use beans inside beans (components inside other components)!
	private JwtProvider jwtProvider;
	@Autowired
	private UserDetailsService userDetailsService;

	/*
	The doFilterInternal method, we are calling the getJwtFromRequest
	method which retrieves the Bearer Token (ie. JWT) from the HttpServletRequest
	object the user had made that we are passing as input. Once we retrieve the token,
	we pass it to the validateToken() method of the JwtProvider class that makes sure
	the token is still valid.
	Once the JWT is validated, we retrieve the username from the token by calling the getUsernameFromJWT() method.
	After we get the username, we retrieve the user using the UserDetailsService class
	and store the user inside the SecurityContext.
	 */
	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
	                                @NotNull FilterChain filterChain) throws ServletException, IOException {
		String jwt = getJwtFromRequest(request);

		if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) { // The validateToken method uses the JwtParser class to validate our JWT.
			String username = jwtProvider.getUsernameFromJWT(jwt);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return bearerToken;
	}
}