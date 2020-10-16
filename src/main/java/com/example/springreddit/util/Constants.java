package com.example.springreddit.util;
import lombok.experimental.UtilityClass;

/*
	A class to contain our constants.
 */
// As this is a Utility Class, we have annotated this class with @UtilityClass which is a Lombok annotation
@UtilityClass
public class Constants {
	// Port 8080 is not our MySql Server port (3306) but the port that spring uses.
	public static final String ACTIVATION_EMAIL = "http://localhost:8080/api/auth/accountVerification";
	public static final String JKS_FILE_PASSWORD = "123123";
}