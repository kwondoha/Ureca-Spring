package com.mycom.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.entity.Student;

// Spring Data JPA Layer 추가
// Service -> Repository -> Spring Data JPA -> JPA (Hibernate) -> JDBC
// Spring Data JPA 를 이용한 DB Access
public interface StudentRepository extends JpaRepository<Student, Integer>{
	// CRUD
	// 등록, 수정, 삭제, 목록, 상세 <= 메소드 선언 필요? XXXXXXXXXXXX
	
	// 다양한 select 지원
	// 약속된 규칙을 사용 - findBy______
	// sql, jpql 작성 X, 메소드 선언 O
	
	//  이름 일치 - where name = ?
	List<Student> findByName(String name);
	
	//  이메일 and 전화번호 - where email = ? and phone = ?
	List<Student> findByEmailAndPhone(String email, String phone);	
	
	//  이메일 or 전화번호 - where email = ? or phone = ?
	List<Student> findByEmailOrPhone(String email, String phone);		
	
	// Like 와 Like 와 유사한 것들
	
	//  이름 StartingWith where name like ?%
	List<Student> findByNameStartingWith(String name);	
	
	//  이메일 EndingWith where email like %?
	List<Student> findByEmailEndingWith(String email);	
	
	//  전화번호 Containing where phone like %?%
	List<Student> findByPhoneContaining(String phone);	
	
	// 정렬, between

	//  이름 내림차순 정렬 OrderByNameDesc - order by name desc
	List<Student> findAllByOrderByNameDesc();	// findAll
	
	//  id 범위 findByIdBetween - where id between ~ and ~
	List<Student> findByIdBetween(int from, int to);	
}







