package com.mycom.myapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;
import com.mycom.myapp.service.StudentServiceCrud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// 현재 이 프로젝트는 코드의 복잡도를 줄이기 위해 Dto 를 생략하고 Entity 를 Dto 처럼 함께 사용
// 이후 프로젝트에서 개선될 예정
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name="기본 Student CRUD REST API", description="Student의 등록, 수정, 삭제, 조회, 상세 조회 기능을 REST API로 제공합니다")
public class StudentControllerCrud {

	// 생성자 DI with Lombok
	private final StudentServiceCrud studentServiceCrud;
	
	// 목록
	@GetMapping("/students")
	@Operation(summary="학생 목록",description="전체 학생 목록을 응답합니다.")
	public StudentResultDto listStudent(){
		return studentServiceCrud.listStudent();
	}
	
	// 상세
//	@GetMapping("/students/{id}")
//	public StudentResultDto detailStudent(@PathVariable("id") Integer id){
//		return studentServiceCrud.detailStudent(id);
//	}
	
	// 등록
	@PostMapping("/students")
	@Operation(summary="학생 등록",description="신규 학생 1명을 등록합니다.")
	public StudentResultDto insertStudent(StudentDto studentDto) {
		return studentServiceCrud.insertStudent(studentDto);
	}
	
	// 수정
	@PutMapping("/students/{id}")
	@Operation(summary="학생 수정",description="학생 1명을 수정합니다.")

	public StudentResultDto updateStudent(@PathVariable("id") Integer id, StudentDto studentDto){
		studentDto.setId(id); // StudentDto 객체에 id가 포함될 수는 있지만, 우선순위는 PathVariable의 id로 하고 명시적으로 setId() 호
		return studentServiceCrud.updateStudent(studentDto);
	}
	
	// 삭제
	@DeleteMapping("/students/{id}")
	@Operation(summary="학생 삭제",description="학생 1명을 삭제합니다.")
	public StudentResultDto deleteStudent(@PathVariable("id") Integer id) {
		return studentServiceCrud.deleteStudent(id);
	}
	
	// 전체 건수
	@GetMapping("/students/count")
	@Operation(summary="학생 전체 건수 조회",description="학생 전체 건수를 응답합니다.")
	public StudentResultDto countStudent() {
		return studentServiceCrud.countStudent();
	}
	
	// 페이징 목록
	// pageNumber 첫 페이지 0
	@GetMapping("/students/page/{pageNumber}/{pageSize}")
	@Operation(summary="학생 페이징 목록", description="페이징 요청된 학생 목록을 응답합니다/")
	public StudentResultDto listStudent(
		@PathVariable("pageNumber") Integer pageNumber,
		@PathVariable("pageSize") Integer pageSize
	){
		return studentServiceCrud.listStudent(pageNumber, pageSize);
	}
}