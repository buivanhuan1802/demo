package com.easywork.api.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.easywork.api.exception.ICustomException;
import com.easywork.api.exception.LoginExceptionHandling;
import com.easywork.api.jwt.JWTProvider;
import com.easywork.api.serviceimpl.AppUserServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final JWTProvider tokenProvider;

	private final AppUserServiceImpl appUser;
	private final String url;

	public JWTLoginFilter(String url, AuthenticationManager authManager, JWTProvider tokenprovider,
			AppUserServiceImpl appUser) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.tokenProvider = tokenprovider;
		this.appUser = appUser;
		this.url = url;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password,
				appUser.loadUserByUsername(username).getAuthorities()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// Write Authorization to Headers of Response.
		String jwt = this.tokenProvider.createToken(authResult);
		System.out.println(jwt);
		tokenProvider.addAuthentication(response, jwt);
		response.setStatus(HttpStatus.OK.value());

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		ICustomException authenticationError = new LoginExceptionHandling(HttpStatus.UNAUTHORIZED, "Unauthenticated",
				new String[] { "Bad credentials" }, this.url);
		authenticationError.responseException(response);

	}
}