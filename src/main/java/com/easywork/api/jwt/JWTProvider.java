package com.easywork.api.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.easywork.api.exception.ICustomException;
import com.easywork.api.exception.JWTExceptionHandling;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTProvider {
	@Autowired
	private Environment env;

	private final long getExpirationTime() {
		long expirationTime = 216_000;
		try {
			return Long.parseLong(env.getProperty("api.jwt.expire_time"));
		} catch (NumberFormatException e) {
			return expirationTime;
		}
	}

	private final String getSecret() {
		String secret = env.getProperty("api.jwt.secret_key");
		if (StringUtils.hasText(secret))
			return secret;
		return "haha";
	}

	private final String getHeaderString() {
		String headerString = env.getProperty("api.jwt.header_string");
		if (StringUtils.hasText(headerString))
			return headerString;
		return "Authorization";

	}

	private final String getTokenPrefix() {
		String tokenPrefix = env.getProperty("api.jwt.token_prefix");
		if (StringUtils.hasText(tokenPrefix))
			return tokenPrefix;
		return "TokenPrefix";

	}

	private final String getAuthoritiesKey() {
		String authoritiesKey = env.getProperty("api.jwt.authorities_key");
		if (StringUtils.hasText(authoritiesKey))
			return authoritiesKey;
		return "AuthoritiesKey";

	}

	public String createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.joining(","));
		ZonedDateTime now = ZonedDateTime.now();
		Date issueDate = Date.from(now.toInstant());
		return Jwts.builder().setSubject(authentication.getName()).claim(this.getAuthoritiesKey(), authorities)
				.signWith(SignatureAlgorithm.HS512, this.getSecret()).setIssuedAt(issueDate)
				.setExpiration(new Date(System.currentTimeMillis() + this.getExpirationTime())).compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.getSecret()).parseClaimsJws(token).getBody();
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(claims.get(this.getAuthoritiesKey()).toString().split(",")).stream()
				.map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}


	public boolean validateToken(String authToken, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		ICustomException jwtException = null;
		try {
			Jwts.parser().setSigningKey(this.getSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			jwtException = new JWTExceptionHandling(HttpStatus.UNAUTHORIZED, "Jwt Exception",
					new String[] { "Invalid JWT Signature" }, request.getRequestURI());
		} catch (MalformedJwtException ex) {
			jwtException = new JWTExceptionHandling(HttpStatus.UNAUTHORIZED, "Jwt Exception",
					new String[] { "Invalid JWT token" }, request.getRequestURI());
		} catch (ExpiredJwtException ex) {
			jwtException = new JWTExceptionHandling(HttpStatus.UNAUTHORIZED, "Jwt Exception",
					new String[] { "Access token is expired" }, request.getRequestURI());
		} catch (UnsupportedJwtException ex) {
			jwtException = new JWTExceptionHandling(HttpStatus.UNAUTHORIZED, "Jwt Exception",
					new String[] { "Unsupported JWT" }, request.getRequestURI());
		} catch (IllegalArgumentException ex) {
			jwtException = new JWTExceptionHandling(HttpStatus.UNAUTHORIZED, "Jwt Exception",
					new String[] { "Jwt claims string is empty" }, request.getRequestURI());
		}
		if (jwtException != null) {
			jwtException.responseException(response);
		}
		return false;
	}

	public void addAuthentication(HttpServletResponse res, String jwt) {
		res.addHeader(this.getHeaderString(), this.getTokenPrefix() + " " + jwt);
	}

	public String resolveToken(HttpServletRequest request) {
		String token = request.getHeader(this.getHeaderString());
		if (StringUtils.hasText(token) && token.startsWith(this.getTokenPrefix())) {
			String jwt = token.substring(this.getTokenPrefix().length() + 1, token.length());
			return jwt;
		}
		return null;
	}
}