package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	// csrf 활성화
	// 회원 가입 화면 처리, 가입 처리 permitAll()
	@Bean
	SecurityFilterChain filterChain(
			HttpSecurity http
	) throws Exception{
		return http.authorizeHttpRequests( request -> request
										.requestMatchers(
											"/", 
											"/index.html",
											"/.well-known/**",
											"/token/**"
										).permitAll()
										.anyRequest().authenticated()
				)
				.csrf( csrf -> csrf.spa()) 
				.build();
	}	
}
