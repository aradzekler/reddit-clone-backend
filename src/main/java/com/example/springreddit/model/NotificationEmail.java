package com.example.springreddit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/*
	NotificationEmail entity, containing Lombok annotations for less mess!
	The NotificationEmail class handles the email data when a user gets a notification
	(update) for a subreddit.

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotificationEmail {
	private String subject;
	private String recipient;
	private String body;
}