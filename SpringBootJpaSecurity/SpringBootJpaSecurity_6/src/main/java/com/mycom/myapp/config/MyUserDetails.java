package com.mycom.myapp.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

// UserDetail 인터페이스 3개의 추상 메소드 구현은 나머지 필드와 함께 @Getter로 처리
@Builder
@Getter
public class MyUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	// Spring Security가 필요로 하는 필드 3개
	
	// 로그인 식별자. 프로젝트6에서는 email과 동일하게 사용 
	private final String username;	
	// DB Bcrypt 인코딩, 로그인 시 사용자 입력 비밀번호와 Spring Security가 매칭 확인
	// 사용자 입력 문자열 -> PasswordEncoder를 이용해서 인코딩 비교 
	private final String password;	
	// CUSTOMER, ADMIN을 포함하는 문자열 배열을 메소드에 전달해서 UserDetails 객체를 만들때  
	// 자동으로 ROLE_prefix가 붙어서 (ROLE_CUSTOMER, ..) GrantedAuthority가 생성됨
	// loadUserByUsername()의 User.builder().roles() 
	private final Collection<? extends GrantedAuthority> authorities;
	
	// 비즈니스 로직상 필요한 추가 필드 3개
	private final Long id;
	private final String name;
	private final String email;
}
