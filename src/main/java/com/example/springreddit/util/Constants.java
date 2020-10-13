package com.example.springreddit.util;
import lombok.experimental.UtilityClass;

/*
	A class to contain our constants.
 */
// As this is a Utility Class, we have annotated this class with @UtilityClass which is a Lombok annotation
@UtilityClass
public class Constants {
	public static final String ACTIVATION_EMAIL = "http://localhost:8080/api/auth/accountVerification";
}