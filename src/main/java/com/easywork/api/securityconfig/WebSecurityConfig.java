package com.easywork.api.securityconfig;

import com.easywork.api.filter.AuthenticationFilter;
import com.easywork.api.filter.JWTLoginFilter;
import com.easywork.api.jwt.JWTProvider;
import com.easywork.api.serviceimpl.AppUserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserServiceImpl appUser;

	@Autowired
	private JWTProvider tokenProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				// No need authentication.
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				// Need authentication.
				.anyRequest().authenticated().
//
				and()
				.addFilterBefore(
						new JWTLoginFilter("/login", authenticationManager(), this.tokenProvider, this.appUser),
						UsernamePasswordAuthenticationFilter.class)
				//
				// Add Filter 2 - JWTAuthenticationFilter
				//
				.addFilterBefore(new AuthenticationFilter(this.tokenProvider),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(appUser).passwordEncoder(passwordEncoder());

	}

}