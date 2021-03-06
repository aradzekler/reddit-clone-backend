package com.example.redditclonebackend;

import com.example.redditclonebackend.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // enables Asynchronous calls, shortening out waiting time for stuff.
@Import(SwaggerConfiguration.class)
public class SpringRedditApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.example.redditclonebackend.SpringRedditApplication.class, args);
	}

}