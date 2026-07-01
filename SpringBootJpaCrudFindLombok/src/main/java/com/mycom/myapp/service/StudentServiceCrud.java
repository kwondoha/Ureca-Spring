package com.mycom.myapp.service;

import java.util.List;
import java.util.Optional;

import com.mycom.myapp.entity.Student;

public interface StudentServiceCrud {

	// 목록
	List<Student> listStudent();
	
	// 상세
	Optional<Student> detailStudent(int id);
	
	// 등록
	// int 가 아닌 영속화된 Student 객체를 리턴
	// 파라미터 객체는 영속화 X 일반 자바 객체
	// 리턴 객체는 영속화 O 
	Student insertStudent(Student student);
	
	// 수정
	// 파라미터 객체의 id 가 있다. 이 id 는 DB 에는 없을 수도 있다. 리턴 Optional
	Optional<Student> updateStudent(Student student);
	
	// 삭제
	// 영속화된 객체 리턴 X
	void deleteStudent(int id);
	
	// 추가
	long countStudent(); // 총 건수
	List<Student> listStudent(int pageNumber, int pageSize); // 페이징된 리스트
}