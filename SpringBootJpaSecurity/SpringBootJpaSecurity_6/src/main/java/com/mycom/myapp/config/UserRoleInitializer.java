package com.mycom.myapp.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.mycom.myapp.user.entity.UserRole;
import com.mycom.myapp.user.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

// 스프링 컨텍스트 완료 직후
// 1번 실행
// CUSTOMER, ADMIN 2개 user_role 이 없으면 추가
@Component
@RequiredArgsConstructor
public class UserRoleInitializer implements ApplicationRunner{

	private final UserRoleRepository userRoleRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		createRoleIfAbsent("CUSTOMER");
		createRoleIfAbsent("ADMIN");
	}

	// name 으로 테이블에 해당 이름의 role 이 없으면 추가
	private void createRoleIfAbsent(String name) {
		if( userRoleRepository.findByName(name) == null ) {
			UserRole role = new UserRole(); // NEW, 영속화 X
			role.setName(name);
			userRoleRepository.save(role);
		}
	}
}










