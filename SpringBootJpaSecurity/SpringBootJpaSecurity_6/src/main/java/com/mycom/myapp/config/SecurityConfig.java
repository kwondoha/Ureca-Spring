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

// _4 프로젝트
// 사용자 정의 login.html 에서 login 을 form -> fetch (data 요청, 응답 )
// Spring Data Jpa 를 이용한 회원 가입 처리 <= 가입한 회원으로 login X ( _5 처리) 
// 로그인은 dskim/1234 하드 코딩 방식
@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// csrf 활성화
	// 회원 가입 화면 처리, 가입 처리 permitAll()
	@Bean
	SecurityFilterChain filterChain(
			HttpSecurity http,
			MyAuthenticationSuccessHandler successHandler,
			MyAuthenticationFailureHandler failureHandler
	) throws Exception{
		return http.authorizeHttpRequests( request -> request
										.requestMatchers(
											"/", 
											"/index.html",
											"/.well-known/**",
											"/login",
											"/login.html",
											
											"/register",
											"/register.html",
											"/users/**",
											
											"/csrf-token"
										).permitAll()
										.requestMatchers("/customer/**").hasAnyRole("CUSTOMER", "ADMIN")
										.requestMatchers("/admin/**").hasRole("ADMIN")
										.anyRequest().authenticated() // 로그인 필요
				)
				.csrf( csrf -> csrf.spa()) // Spring Security 의 CsrfFilter 동작 <= 최초 request 에 XSRF-TOKEN 이름의 쿠키를 내려 보낸다.
				.formLogin( form -> form
									.loginPage("/login.html")
									.loginProcessingUrl("/login")
//									.defaultSuccessUrl("/") // form 에 의한 페이지 요청 X, 대신 fetch 로 데이터 요청 O
									.successHandler(successHandler) // fetch 로 데이터 요청 성공했을 때 처리자
									.failureHandler(failureHandler) // fetch 로 데이터 요청 실패했을 때 처리자
									.permitAll()
				) 
//				.logout(logout -> logout.permitAll()) // post /login 요청에 대해서만 처리, get 지원 X
				// 백앤드는 페이지 요청과 데이터 요청을 구분하지 못함
				// 현재 구현 코드 기준으로 get /logout은 페이지 요청, post /logout은 데이터 요청을 전제로 코드 변경
				.logout(logout -> logout // get, post /logout 처리
						.logoutRequestMatcher(new OrRequestMatcher(
								PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/logout"),
								PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/logout")))
						.logoutSuccessHandler( (request, response, authentication ) -> { 
							if ("GET".equals(request.getMethod())) { // 페이지 요청의 logout 
								response.sendRedirect("/logout.html");
								return;
							}
							// 데이터 요청의 logout 
							response.setStatus(HttpServletResponse.SC_OK);
							response.setContentType("application/json");
							response.getWriter().write("""
										{"result":"success"}
									""");
						})
						.permitAll()
						
				)
				.build();
	}	
}
