package com.mycom.myapp.config;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.entity.UserRole;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// 1. dskim/1234 hard coding -> DB Access
// 2. DB Access 는 username 에 해당하는 필드를 우리는 email 사용 <= UserRepository 에 findByEmail()
// 3. UserDetails 구현 객체 ( Spring Security Format, Spring Security 인식하는 표준 User 객체 )
// 4. UserDetails 구현 클래스를 직접 작성 X, Spring Security 의 User 를 대신 사용
// 5. 우리 코드의 User 와 Spring Security 의 User 를 구별
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	// 사용자에게 로그인 창에서 email, password 방식을 사용
	// email <- username 이 동일.
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if( optionalUser.isPresent() ) {
			User user = optionalUser.get();
			List<UserRole> listUserRole = user.getUserRoles();
			
			// _5 기준 ListUserRole -> String[] roleStrArray 만들어서 아래 메소드를 통해 전달
			// 내부적으로 ROLE_CUSTOMER 형식으로 ROLE_ prefix 처리하고 내부적으로 GrandedAuthority Collection 처리
			// org.springframework.security.core.userdetails.User.builder()..roles(roleStrArray)
			// 이제 MyUserDetails 객체를 사용하므로 우리가 직접 GrandedAuthority Collection 을 만들어 한다.
			List<SimpleGrantedAuthority> authorities = listUserRole.stream()
														.map(UserRole::getName)
														.map(name -> "ROLE_" + name)
														.map(SimpleGrantedAuthority::new)
														.toList();
			return MyUserDetails.builder()
					.username(user.getEmail())  	// Spring Security 계약 필드
					.password(user.getPassword()) 	// Spring Security 계약 필드
					.authorities(authorities) 		// Spring Security 계약 필드
					.id(user.getId())
					.name(user.getName())
					.email(user.getEmail())
					.build();
		}
		
		throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
	}



}
