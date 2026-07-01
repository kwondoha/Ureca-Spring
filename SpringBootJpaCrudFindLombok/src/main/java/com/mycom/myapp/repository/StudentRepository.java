package com.mycom.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.entity.Student;

// Spring Data JPA Layer 추가
// Service -> Repository -> Spring Data JPA -> JPA(Hibernate) -> JDBC 
// Spring Data JPA 를 이용한 DB Access (한번더 추상화~?)
public interface StudentRepository extends JpaRepository<Student, Integer> {
	// CRUD
	// 등록, 수정, 삭제, 목록, 상세 <- 메소드 선언할 필요 없음 
	
}
