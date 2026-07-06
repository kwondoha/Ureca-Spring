package com.mycom.myapp.service;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;

// 모두 StudentResultDto 를 리턴
public interface StudentServiceCrud {

	// 목록
	StudentResultDto listStudent();
	
	// 상세
//	StudentResultDto detailStudent(int id);
	
	// 상세 - StudentDto return
	StudentDto detailStudent(int id);
	
	// 등록

	StudentResultDto insertStudent(StudentDto studentDto);
	
	// 수정
	StudentResultDto updateStudent(StudentDto studentDto);
	
	// 삭제
	StudentResultDto deleteStudent(int id);
	
	// 추가
	StudentResultDto countStudent(); // 총 건수
	StudentResultDto listStudent(int pageNumber, int pageSize); // 페이징된 리스트
}
