package com.example.redditclonebackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/*
	Using this class we are able to configure values for the whole app
 */
@Component
@EnableConfigurationProperties // takes information from our resources/*.properties files
@ConfigurationProperties(prefix = "app")
public class AppConfig {
	@NotNull
	private String url;

	public String getAppUrl() {
		return url;
	}

	public void setUrl(String url) { this.url = url;}
}