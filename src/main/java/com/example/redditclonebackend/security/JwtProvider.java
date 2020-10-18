package com.example.redditclonebackend.security;

import com.example.redditclonebackend.exception.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static com.example.redditclonebackend.util.Constants.JKS_FILE_PASSWORD;
import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;


/* Our Jwt provider service, this one constructs our Jwt tokens and
	retrieving private keys. These authenticated and validated tokens
	are used to transfer requests between the client and the server.
 */
@Service
public class JwtProvider {

	private KeyStore keyStore;

	@Value("${jwt.expiration.time}") // jwt.expiration.time == 900000 ms (15 minutes)
	private Long jwtExpirationInMillis;


	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, JKS_FILE_PASSWORD.toCharArray()); // 123123 is the password for the jks
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occurred while loading keystore", e);
		}

	}

	// our Token generation method.
	public String generateToken(Authentication authentication) {
		org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.setIssuedAt(from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis))) // setting our short lived expiration on JWT.
				.compact();
	}

	public String generateTokenWithUserName(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}

	// retrieving public keys from keystore
	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("springblog", JKS_FILE_PASSWORD.toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new SpringRedditException("Exception occurred while retrieving private key from keystore.");
		}
	}

	// a method for validating a token inside a jwt.
	public boolean validateToken(String jwt) {
		parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}

	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		} catch (KeyStoreException e) {
			throw new SpringRedditException("Exception occurred while retrieving public key from keystore.");
		}
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = parser()
				.setSigningKey(getPublicKey())
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}

	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}

}