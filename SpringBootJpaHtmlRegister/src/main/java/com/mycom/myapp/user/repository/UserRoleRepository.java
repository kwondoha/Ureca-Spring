package com.mycom.myapp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.user.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	// 기본 CRUD는 자동 구현
	
	// 문자열(이름0으로부터 userRole을 가져오는 findBy~ 처리
	// Optional X
	UserRole findByName(String name);

}
