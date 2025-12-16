package com.alimberdi.flowforge.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils  {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long jwtExpiration;

	private Key signingKey;

	@PostConstruct
	public void init() {
		if (secret == null) {
			throw new IllegalStateException("JWT secret not set");
		}
		if (secret.length() < 16) {
			throw new IllegalStateException("JWT secret must be at least 16 characters");
		}
		this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateToken(String subject) {
		return Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(signingKey, SignatureAlgorithm.HS256)
				.compact();
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(signingKey).build()
				.parseClaimsJws(token).getBody();
		return resolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public boolean isTokenExpired(String token) {
		Date exp = extractClaim(token, Claims::getExpiration);
		return exp.before(new Date());
	}

	public boolean validateToken(String token, String username) {
		String subject = extractUsername(token);
		return subject.equals(username) && !isTokenExpired(token);
	}

}
