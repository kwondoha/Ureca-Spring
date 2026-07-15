package com.mycom.myapp.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

// UserDetails 인터페이스의 3개 추상 메소드 구현은 나머지 필드와 함께 @Getter 로 처리
@Builder
@Getter
public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	// 아래 3개는 Spring Security 가 필요로 하는 필드
	
	// 로그인 식별자. 현재 프로젝트는 email 과 동일하게 사용
	private final String username;
	
	// DB BCrypt 인코딩, 로그인 시 사용자 입력 비밀번호와 Spring Security 가 매칭 확인
	// 사용자 입력 문자열 -> PasswordEncoder 를 이용해서 인코딩 비교
	private final String password;

	// CUSTOMER, ADMIN 을 포함하는 문자열 배열을 아래 메소드에 전달해서 UserDetails 객체를 만들 때
	// 자동으로 ROLE_ prefix 가 붙어서 (ROLE_CUSTOMER, ...)  GrantedAuthority 가 만들어 진다.
	// loadUserByUsername() 의 User.builder().roles()
	private final Collection<? extends GrantedAuthority> authorities;
	
	// 아래 3개는 비즈니스 로직상 필요한 추가 필드
	// 더 필요한 항목들 추가 가능
	private final Long id;
	
	private final String name;
	
	private final String email;

}










