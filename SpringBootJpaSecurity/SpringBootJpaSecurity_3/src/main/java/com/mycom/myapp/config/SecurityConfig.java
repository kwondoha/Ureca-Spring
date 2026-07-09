package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// 프로젝트3
// 사용자 정의 login.html 사용 
@Configuration
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		// "/.well-known/**"    <<-- Chrome 등 다양한 브라우저가 원활한 서버와 통신을 위해서 사용자의 요청 외 다양한 요청 시도 <- 이 요청들이 가지는 패턴
		return http.authorizeHttpRequests(request -> request
					.requestMatchers("/", "/index.html", "/.well-known/**","/login","/login.html").permitAll() // 로그인 불필요
					// Role 기반 처리, 로그인 필요
					// 로그인하더라도 필요한 Role이 있어야 접근 가능
					.requestMatchers("/customer/**").hasAnyRole("CUSTOMER","ADMIN")
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated() // 로그인 필요
			)
			.csrf(csrf -> csrf.disable()) // csrf 처리 X, 프로젝트3은 사용자 지정 html 페이지 사용하기때문에 
			.formLogin(form -> form.loginPage("/login.html")
									.loginProcessingUrl("/login")
									.defaultSuccessUrl("/", true)
									.permitAll()
			) 
			// /logout 경로로 요청을 하면 LogourFilter가 가로채서 HttpSession invalidate, SecurityContext의 보관 인증 정보 제거
			// /logout permitAllL()
			.logout(logout -> logout.permitAll())
			.build();
	}
}
