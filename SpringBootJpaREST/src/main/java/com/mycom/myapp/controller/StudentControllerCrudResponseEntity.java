package com.mycom.myapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.RequiredArgsConstructor;

// ResponseEntity 객체를 return
// 일종의 data wrapper
// ResponseEntity 객체는 Http 응답 코드를 설정 <= Http Response 코드가 변경 <= 개발자 코드가 Http Response 에 능동적으로 개입
@RestController
@RequestMapping("/api/re")
@RequiredArgsConstructor
public class StudentControllerCrudResponseEntity {

	// 생성자 DI with Lombok
	private final StudentServiceCrud studentServiceCrud;
	
	// 목록
	// 현재 응답 ResultDto 인 것을 그대로 사용하면서 ResponseEntity 객체를 활용 연습
	@GetMapping("/students")
	public ResponseEntity<StudentResultDto> listStudent(){
		
		// 다양한 응답 설정 방법이 있다.
		StudentResultDto studentResultDto = studentServiceCrud.listStudent();
//		return new ResponseEntity<StudentResultDto>(studentResultDto, HttpStatus.OK);
//		return new ResponseEntity<StudentResultDto>(studentResultDto, HttpStatus.NOT_FOUND); // 404
//		return new ResponseEntity<StudentResultDto>(studentResultDto, HttpStatus.INTERNAL_SERVER_ERROR); // 500
		
		// 다른 표현들
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(studentResultDto);
		
//		return ResponseEntity
//				.ok() // == .status(HttpStatus.OK)
//				.body(studentResultDto);	
		
		// 404 처리
		// body 에 null 처리
//		return new ResponseEntity<StudentResultDto>((StudentResultDto) null, HttpStatus.NOT_FOUND); // 404
		
//		return ResponseEntity
//				.status(HttpStatus.NOT_FOUND)
//				.body(null);
		
//		return ResponseEntity
//				.status(HttpStatus.NOT_FOUND)
//				.build();	// == body(null)
		
		// 500 처리
		// body 에 null 처리
//		return ResponseEntity
//				.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.body(null);

//		return ResponseEntity
//				.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.build();	
		
	}
	
	// 상세
	// ~ResultDto 와 ResponseEntity 함께 사용 방법
	// Service Layer ResultDto 활용
	// Controller Layer ResponseEntity 활용
	// 만약, ~ResultDto 를 사용하지 않을 경우
	//   ServiceLayer 가 ~ResultDto 를 사용 X, try-catch 사용 X
	//   예외 발생 => Spring Boot 의 기본 예외 처리, GlobalExceptionHandler 활용
//	@GetMapping("/students/{id}")
//	public ResponseEntity<StudentDto> detailStudent(@PathVariable("id") Integer id){
//		StudentResultDto studentResultDto = studentServiceCrud.detailStudent(id);
//		
//		// 프론트 상세 제어를 테스트 코드
////		studentResultDto.setResult("notfound");
//		
//		switch(studentResultDto.getResult()) {
//			// 현재는 success, fail 만 구현된 상태지만 더 다양한 상태를 관리한다고 가정
//			case "success" : return ResponseEntity.ok().body(studentResultDto.getDto());
//			case "notfound" : return ResponseEntity.notFound().build();
//			case "fail" :
//			default : return ResponseEntity.internalServerError().build();
//		}
//	}
	
	// 상세 ~ StudentResultDto 사용 X
	// 오류나는 다른 컨트롤러의 detailStudent() 주석 처리 ( StudentControllerCrud, StudentControllerCrudJsonRequest)
	// 정상적인 상세 요청
	// id 가 없는 요청 => GlobalExceptionHandler 에 의해 처리되지 않는 일반 예외 포맷 리턴
/*
{
    "timestamp": "2026-07-06T05:18:30.721Z",
    "status": 500,
    "error": "Internal Server Error",
    "trace": "com.mycom.myapp.exception.StudentNotFoundException: 학생을 찾을 수 없습니다....."
    "message": "학생을 찾을 수 없습니다. id=200",
    "path": "/api/re/students/200"
}	
 */
	
	@GetMapping("/students/{id}")
	public ResponseEntity<StudentDto> detailStudent(@PathVariable("id") Integer id){
		StudentDto studentDto = studentServiceCrud.detailStudent(id);
//		return ResponseEntity.ok().body(studentDto);
		return ResponseEntity.ok(studentDto);
		
	}
	
	
	// 등록
	@PostMapping("/students")
	public ResponseEntity<String> insertStudent(StudentDto studentDto) {
		StudentResultDto studentResultDto =  studentServiceCrud.insertStudent(studentDto);
		switch(studentResultDto.getResult()) {
			case "success" : return ResponseEntity.ok().build();
			case "fail" :
			default : return ResponseEntity.internalServerError().build();
		}
	}
	
	// 수정
	@PutMapping("/students/{id}")
	public ResponseEntity<String> updateStudent(@PathVariable("id") Integer id, StudentDto studentDto){
		studentDto.setId(id); // StudentDto 객체에 id 가 포함될 수 있지만, 우선 순위는 PathVariable 의 id 로 하고 명시적으로 setId() 호출
		StudentResultDto studentResultDto =  studentServiceCrud.updateStudent(studentDto);
		switch(studentResultDto.getResult()) {
			case "success" : return ResponseEntity.ok().build();
			case "fail" :
			default : return ResponseEntity.internalServerError().build();
		}
	}
	
	// 삭제
	@DeleteMapping("/students/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") Integer id) {
		StudentResultDto studentResultDto =  studentServiceCrud.deleteStudent(id);
		switch(studentResultDto.getResult()) {
			case "success" : return ResponseEntity.ok().build();
			case "fail" :
			default : return ResponseEntity.internalServerError().build();
		}
	}
	
	// 전체 건수
	@GetMapping("/students/count")
	public StudentResultDto countStudent() {
		return studentServiceCrud.countStudent();
	}
	
	// 페이징 목록
	// pageNumber 첫 페이지 0
	@GetMapping("/students/page/{pageNumber}/{pageSize}")
	public StudentResultDto listStudent(
		@PathVariable("pageNumber") Integer pageNumber,
		@PathVariable("pageSize") Integer pageSize
	){
		return studentServiceCrud.listStudent(pageNumber, pageSize);
	}
}


















