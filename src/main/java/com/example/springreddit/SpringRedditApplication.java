package com.example.springreddit;

//import com.example.springreddit.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@Import(SwaggerConfiguration.class)
public class SpringRedditApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.example.springreddit.SpringRedditApplication.class, args);
	}

}