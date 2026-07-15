package com.mycom.myapp.auth.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.mycom.myapp.auth.dto.LoginResultDto;
import com.mycom.myapp.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// AuthenticationManager 에게 UsernamePasswordAuthenticationToken 전달

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService{

	// AuthenticationManager <= 인증 처리
	// DI 이 가능하려면 @Bean 등록 <= SecurityConfig
	private final AuthenticationManager authenticationManager;
	
	// 인증 성공하면 Token 발급
	private final JwtUtil jwtUtil;
	
	@Override
	public LoginResultDto login(String email, String password) {
		LoginResultDto loginResultDto = new LoginResultDto();
		
		try {
			// 인증 시도
			// 미인증된 UsernamePasswordAuthenticationToken(email, password) 객체를 AuthenticationManager 에게 전달
			// AuthenticationManager -> DaoAuthenticationProvider 위임.
			// DaoAuthenticationProvider -> UserDetailsService.loadUserByUsername() 호출, UserDetails 객체를 얻는다.
			// UserDetails 객체를 통해서 성공, 실패 확인
			// 실패일 경우  AuthenticationException 발생
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password)
			);
			
			// 인증 성공 -> JWT 발급 <= username, List<String roles 필요
			String username = authentication.getName(); // principal
			List<String> roles = authentication.getAuthorities().stream()
									.map(GrantedAuthority::getAuthority).toList();
			
			String token = jwtUtil.createToken(username, roles);
			loginResultDto.setResult("success");
			loginResultDto.setToken(token);
			log.info("Login successed for {}", email);
		}catch(Exception e) {
			//  AuthenticationException 발생
			loginResultDto.setResult("fail");
			log.warn("Login failed for {}", email);
		}
		
		return loginResultDto;
	}

}















