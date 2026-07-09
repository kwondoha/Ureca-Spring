package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// SecurityFilterChain을 HttpSecurity 설정을 통해서 만들고 리턴한다 
	
	// #1. SecurityConfig 설정이 없을 때 :: 모든 요청에 대해 basic form login 필요
	
	// #2. 아래 설정 :: 모든 요청에 대해 login 없이 허락
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		// 인증 필요한 요청 전체에 대해 허락하는 정책을 기준으로 SecurityFilterChain 객체를 만들어서 리턴
//		return http.authorizeHttpRequests(request -> request.anyRequest().permitAll()).build();
//	}
	
	// #3. 아래 설정 :: 모든 요청에 대해 인증 필요
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		// 모든 요청에 대해 인증 필요
//		// 인증 방식으로 formLogin() 선택 -> UsernamePasswordAuthenticationFilter 작동
//		// 	-> POST /login with username, password 요청을 가로채서 AuthenticationManager로 전달
//		return http.authorizeHttpRequests(request -> request
//													.anyRequest()
//													.authenticated()
//			)
//			// 다양한 formLogin() 설정 커스터마이징X Default, 개인 login.html X
//			// Customizer.withDefaults()는 인증 후에 사용자 요청 페이지로 이동 localhost:8080/hello -> 인증 -> localhost:8080/hello 처
//			.formLogin(Customizer.withDefaults()) 
//			.build();
//	}
	
	// #4. 아래 설정 :: 모두 허락 + 인증 필요
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		// "/.well-known/**"    <<-- Chrome 등 다양한 브라우저가 원활한 서버와 통신을 위해서 사용자의 요청 외 다양한 요청 시도 <- 이 요청들이 가지는 패턴
		return http.authorizeHttpRequests(request -> request
													.requestMatchers("/", "/index.html", "/.well-known/**").permitAll()
													.anyRequest().authenticated()
			)
			.formLogin(Customizer.withDefaults()) 
			.build();
	}
}
