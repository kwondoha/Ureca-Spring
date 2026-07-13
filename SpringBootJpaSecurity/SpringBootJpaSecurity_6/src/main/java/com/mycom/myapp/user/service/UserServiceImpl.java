package com.mycom.myapp.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.entity.UserRole;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

	// 사용자 등록할 때 CUSTOMER Role 부여 
	
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	
	// 사용자 입력 패스워드 (일반 텍스트) 암호화 후 저장
	// 내맘대로 암호화가 아니라 현재 프로젝트에 설정된 암호화 객체 이용 => DI
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserResultDto insertUser(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		
		// CUSTOMER 이름으로 UserRole
		// UserDto -> User
		// User <- UserRole
		// 패스워드 암호화
		// 저장
		try {
			List<UserRole> userRoles = List.of(userRoleRepository.findByName("CUSTOMER"));
			
			// user 는 영속화 X
			User user = User.builder()
							.name(userDto.getName())
							.email(userDto.getEmail())
							.password(passwordEncoder.encode(userDto.getPassword()))
							.userRoles(userRoles)
							.build();
			User savedUser = userRepository.save(user); // 영속화된 savedUser 리턴
			log.debug("User Registerd: {}", savedUser.getEmail());
			
			userResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			// user, user_user_role 2개 insert 작업에 대한 transaction rollback 처리
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			userResultDto.setResult("fail");
		}
/*
Hibernate: select ur1_0.id,ur1_0.name from user_role ur1_0 where ur1_0.name=?
Hibernate: insert into user (email,name,password) values (?,?,?)
Hibernate: insert into user_user_role (user_id,user_role_id) values (?,?)		
 */
		
		return userResultDto;
	}

}










