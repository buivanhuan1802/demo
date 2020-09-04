package com.easywork.api.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.easywork.api.exception.ICustomException;
import com.easywork.api.exception.JWTExceptionHandling;
import com.easywork.api.jwt.JWTProvider;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	private final JWTProvider tokenProvider;

	public AuthenticationFilter(JWTProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = this.tokenProvider.resolveToken(request);
		if (StringUtils.hasText(jwt)) {
			if (this.tokenProvider.validateToken(jwt, response, request)) {
				Authentication authentication = tokenProvider.getAuthentication(jwt);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		} else {
			ICustomException jwtExceiption = new JWTExceptionHandling(HttpStatus.UNAUTHORIZED, "UnAuthenticated",
					new String[] { "Jwt token needed" }, request.getRequestURI());
			jwtExceiption.responseException(response);

		}
		filterChain.doFilter(request, response);
		this.resetAuthenticationAfterRequest();
	}

	private void resetAuthenticationAfterRequest() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}