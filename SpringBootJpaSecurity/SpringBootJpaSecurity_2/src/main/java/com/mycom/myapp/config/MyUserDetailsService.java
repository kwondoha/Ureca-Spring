package com.mycom.myapp.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	// UserDetails 인터페이스가 추상메소드를 갖고 있음
	// Spring Security는 사용자 인증에 사용하기 위해, UserDetails를 구현한 클래스의 객체를 요구하고
	// 이를 통해서 인증 및 권한 처리
	// 현재 프로젝트2에서는 Spring Security에서 제공하는 User를 사용함 (직접 정의 가능하지만 사용X)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 일반적으로 사용자 DB Access를 통한 UserDetails 객체를 생성 및 리턴
		// 현재 프로젝트2에서는 하드코딩할거임
		// -> admin/1234, customer/1234
		return switch (username) {
			case "admin" -> User.builder()
								.username("admin")
								.password(DemoPasswords.ENCODED)
								.roles("ADMIN")
								.build();
			case "customer" -> User.builder()
									.username("customer")
									.password(DemoPasswords.ENCODED)
									.roles("CUSTOMER")
									.build();
			default -> throw new UsernameNotFoundException("User Not Found");
		};
	}
}
