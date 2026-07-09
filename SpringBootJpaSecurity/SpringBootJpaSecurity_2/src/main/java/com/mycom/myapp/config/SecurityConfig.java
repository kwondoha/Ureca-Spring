package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// 프로젝트2
// UserDetailsService 도입 - DB 연동X, 하드 코딩
//		-> DB 연동 기본 :: password 암호화 -----> 다시 복호화
//						1. 회원가입 : 사용자가 패스워드 1234 등록 -> BCrypt(1234) -> ~~~ (테이블에 저장)
//						2. 로그인  : 사용자가 패스워드 123 사용 -> BCrypt(123) -> ~~~(테이블과 비교 후 로그인 실패) 
//		-> PasswordEncoder DI 제공 (BCryptPasswordEncoder)
// Role 기반 처리 :: 전체 접근 or 일부 접근
// 		-> ADMIN, CUSTOMER
// 아래 설정만 하고 /admin/hello 등을 요청하면 Forbidden, status=403 
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
					.requestMatchers("/", "/index.html", "/.well-known/**").permitAll() // 로그인 불필요
					// Role 기반 처리, 로그인 필요
					// 로그인하더라도 필요한 Role이 있어야 접근 가능
					.requestMatchers("/customer/**").hasAnyRole("CUSTOMER","ADMIN")
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated() // 로그인 필요
			)
			// 로그인에 성공하면 무조건 /로 이동된다 접근 권한은 permitAll()
			.formLogin(form -> form.defaultSuccessUrl("/", true).permitAll()) 
			// /logout 경로로 요청을 하면 LogourFilter가 가로채서 HttpSession invalidate, SecurityContext의 보관 인증 정보 제거
			// /logout permitAllL()
			.logout(logout -> logout.permitAll())
			.build();
	}
}
