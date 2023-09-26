package com.couponcounter.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtProvider {

	// set JWT expiration time to 1 hour (in milliseconds) 1000 * 60 * 60
	private final int jwtExpirationInMs = 1000 * 60 * 60;

	// set the secret key used for signing and verifying the JWT
	private final Key key = Keys
			.hmacShaKeyFor("2F423F4528482B4D6251655468576D5A7133743677397A24432646294A404E63".getBytes());

	// extract the username from the JWT token
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// extract the expiration date from the JWT token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public Object extractRole(String token) {
		Object roles =  extractAllClaims(token).get("userRole");
		return roles;
	}

	// generate a new JWT token for the given username
	public String generateAccessToken(String userName, String email, List<String> list) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("userName", userName);
		claims.put("userRole", list);
		return createToken(claims, email);
	}

	// validate the given JWT token for the given user details
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	// extract a claim from the JWT token using the given claims resolver function
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// extract all the claims from the JWT token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	// create a new JWT token with the given claims and subject
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	// check if the JWT token has expired
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parse(token);
			return true;
		} catch (SignatureException ex) {
			ex.getLocalizedMessage();
		} catch (MalformedJwtException ex) {
			ex.getLocalizedMessage();
		} catch (ExpiredJwtException ex) {
			ex.getLocalizedMessage();
		} catch (UnsupportedJwtException ex) {
			ex.getLocalizedMessage();
		} catch (IllegalArgumentException ex) {
			ex.getLocalizedMessage();
		}
		return false;
	}
}