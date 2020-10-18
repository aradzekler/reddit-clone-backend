package com.example.redditclonebackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine; // thymeleaf for
import org.thymeleaf.context.Context;

/*
	This class is responsible for building up our mail content, using SPRING Thymeleaf
	template engine.
 */
@Service
@AllArgsConstructor
class MailContentBuilder {

	private final TemplateEngine templateEngine;

	// takes our email message as input and it uses the Thymeleaf‘s TemplateEngine to generate the email message.
	String build(String message) {
		Context context = new Context();
		context.setVariable("message", message); // injecting the email message into the HTML template by setting the message into the Context of the TemplateEngine
		return templateEngine.process("mailTemplate", context); // resources/mailTemplate.html
	}
}