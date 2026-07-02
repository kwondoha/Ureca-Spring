package com.mycom.myapp.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	
	private final UserRepository userRepository;
	
	
	@Override
	public UserResultDto login(String email, String password) {
		UserResultDto userResultDto = new UserResultDto();
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
//		Optional<User> optionalUser = userRepository.findById(3L);
		
		// 이메일과 일치하는 사용자 존재
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			// password 비교
			if (user.getPassword().equals(password)) { // 로그인 성공
				
				// UserRole 객체들
				List<String> roles = new ArrayList<>();
				// 아래 코드가 정상적으로 수행된다는 가정. userRepository.findByEmail(email)로 
				// 얻어진 User Entity 객체의 userRoles가 이미 준비되어있다는 전제
				user.getUserRoles().forEach(userRole -> roles.add(userRole.getName()));
				
				// User -> UserDto
				// User Entity의 password는 생략 : 검
				UserDto userDto = UserDto.builder()
						.id(user.getId())
						.name(user.getName())
						.email(user.getEmail())
						.roles(roles)
						.build();
				
				userResultDto.setUserDto(userDto);
				userResultDto.setResult("success");
				
			} else { // 이메일은 일치, password 불일치로 실패
				userResultDto.setResult("fail");
			}
			
		} else { // 이메일과 일치하는 사용자 X
			userResultDto.setResult("fail");
		}
		return userResultDto;
	}
	
}
