package com.example.redditclonebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Web pages you visit make frequent requests to load assets like
	 * images, fonts, and more, from many different places across the
	 * Internet. If these requests for assets go unchecked, the security
	 * of your browser may be at risk. For example, your browser may be
	 * subject to hijacking. As a result, many modern browsers follow
	 * security policies to mitigate such risks.
	 * A request for a resource (like an image or a font) outside of the
	 * origin is known as a cross-origin request. CORS (cross-origin
	 * resource sharing) manages cross-origin requests.
	 */
	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.maxAge(3600L)
				.allowedHeaders("*")
				.exposedHeaders("Authorization")
				.allowCredentials(true);
	}
}