package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mycom.myapp.jwt.JwtAuthenticationFilter;
import com.mycom.myapp.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

// JWT_2 프로젝트 기준
// 백엔드 + 프론트 각각 추가로 검토할 사항
// #1. 백엔드
//     Token 이중화 : Access Token ( 현재 Token, 기본 인증에 사용 ) 외 Refresh Token ( Access Token 의 만료일자가 발급 시 확정되는 단점 해결 - 만료 되면 갱신하기 위한 )
//     로그인 성공 시 2개의 Token 발급, Access Token 만료되면 바로 로그인 하라는 응답 대신 AccessTokenExpiredException 같은 로직 추가
//							, RefreshToken 요청하는 응답 (result: refresh)	
//	   RefreshToken 을 받으면 ( 별도의 처리 ) 인증, 새로운 Access Token 발급 처리
//     만약, RefreshToken 도 만료 => 새로 로그인 필수
//     백엔드는 RefreshToken 을 DB 저장 필수
// #2. 프론트
//     Token 이중화 처리로 로그인 후, Access Token 은 SessionStorage 에 저장, Refresh Token 은 LocalStorage 에 각각 저장 (Access Token 은 쿠키로도 저장하기도 함)
//     Refresh Token 은 일반적 상황에서는 전송 X, RefreshToken 을 전송할 필요가 있는 경우 ( 응답 (result: refresh) ) 전송
//     새로운 Access Token 을 받으면 기존 Token 교체
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtUtil jwtUtil;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// LoginServiceImpl 에서 DI 사용
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// csrf 활성화
	// 회원 가입 화면 처리, 가입 처리 permitAll()
	@Bean
	SecurityFilterChain filterChain(
			HttpSecurity http, 
			MyAuthenticationEntryPoint entryPoint
	) throws Exception{
		return http
				// basicLogin, formLogin 사용 X, csrf X, session X
				.httpBasic(httpBasic -> httpBasic.disable())
				.formLogin(formLogin -> formLogin.disable())
				.csrf(csrf -> csrf.disable())
				.sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests( request -> request
										.requestMatchers(
											"/", 
											"/index.html",
											"/.well-known/**",
											"/login",
											"/login.html",
											
											"/register",
											"/register.html",
											"/users/**",
											
											"/auth/**" // LoginController
										).permitAll()
										.requestMatchers("/customer/**").hasAnyRole("CUSTOMER", "ADMIN")
										.requestMatchers("/admin/**").hasRole("ADMIN")
										.anyRequest().authenticated() // 로그인 필요
				)
				// 로그인 처리 -> LoginServiceImpl 처리
				// 로그인 된 사용자 판별 ( token 검증 ) 이 실패한 경우 예외 처리
				// 우리의 코드에서 이 상황을 처리하는 처리자 필요 <- MyAuthenticationEntryPoint
				.exceptionHandling( exceptionHandling -> exceptionHandling.authenticationEntryPoint(entryPoint))
				// jwt 검증 관련 코드
				// Spring Security 의 기본 인증 필터는 UsernamePasswordAuthenticationFilter ( form login 방식 ) <= jwt 기반이므로 우리의 코드는
				// UsernamePasswordAuthenticationFilter 앞에서 처리할 필요 ( filter chain 단계에서 )
				// Spring Security 는 JWT 인증 필터 제공 X <= 우리가 직접 Filter 생성
				.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
				.build();
	}	
}
