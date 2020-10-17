package com.example.springreddit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
	Swagger is an OPEN API specification that is created as a standard to describe your REST API.
	As we are using Springboot to develop our REST API we can use a library called as Springfox to
	automatically create JSON Documentation.
	We are injecting this configuration to our main SpringRedditApplication to create REST Documentation
	for our application.
	For now the address: http://localhost:8080/swagger-ui.html#/
 */
@Configuration
@EnableSwagger2 // We are specifying the Documentation Type as Swagger2
public class SwaggerConfiguration {

	@Bean
	public Docket redditCloneApi() { // Springfox internal class
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("Reddit Clone API")
				.version("1.0")
				.description("API for Reddit Clone Application")
				.contact(new Contact("Arad Zekler", "http://github.com/aradzekler", "mcspancy@email.com"))
				.license("Apache License Version 2.0")
				.build();
	}
}