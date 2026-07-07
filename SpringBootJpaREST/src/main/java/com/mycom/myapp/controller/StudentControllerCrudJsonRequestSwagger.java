package com.mycom.myapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

// Swagger 전용 interface 
// 이 인터페이스를 구현하는 클래스 코드레벨에 Doc 관련 annotation을 분리 운영
@Tag(name = "JSON Student CRUD REST API", description = "JSON 요청을 통해 Student의 REST API를 제공합니다.")
public interface StudentControllerCrudJsonRequestSwagger {
	// 목록
	@GetMapping("/students")
	@Operation(summary = "학생 목록", description = "전체 학생 목록을 응답합니다.")
	StudentResultDto listStudent();

	// 등록
	@PostMapping("/students")
	@Operation(summary = "학생 등록", description = "JSON 요청을 통해 신규 학생 1명을 등록합니다.")
	StudentResultDto insertStudent(StudentDto studentDto);
}
