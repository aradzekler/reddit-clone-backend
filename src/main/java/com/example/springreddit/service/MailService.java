package com.example.springreddit.service;


import com.example.springreddit.exception.SpringRedditException;
import com.example.springreddit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
class MailService {

	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;

	/*
	 Takes NotificationEmail as input, and inside the method we are creating a MimeMessage
	 by passing in the sender, recipient, subject and body fields. The message body we are
	 receiving from the build() method of our MailContentBuilder class.
	 This is an Asynchronous class! so we would not have to wait about 10 secs for an email.
	 */
	@Async
	void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparation = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(notificationEmail.getBody());
		};
		try {
			mailSender.send(messagePreparation);
			log.info("Activation email sent!!");
		} catch (MailException e) {
			log.error(String.valueOf(e));
			throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
		}
	}

}