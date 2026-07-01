package com.mycom.myapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.entity.Student;
import com.mycom.myapp.service.StudentServiceCrud;

import lombok.RequiredArgsConstructor;


//현재 이 프로젝트는 코드의 복잡도를 줄이기 위해 Dto 를 생략하고 Entity 를 Dto 처럼 함께 사용
//이후 프로젝트에서 개선될 예정

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentControllerCrud {
	
	
	// 생성자 DI with Lombok
	private final StudentServiceCrud studentServiceCrud;
	
	// 목록
	@GetMapping("/list")
	public List<Student> listStudent(){
		return studentServiceCrud.listStudent();
	}
	
	// 상세
	@GetMapping("/detail/{id}")
	public Optional<Student> detailStudent(@PathVariable("id") Integer id){
		return studentServiceCrud.detailStudent(id);
	}
	
	// 등록
	@PostMapping("/insert")
	public Student insertStudent(Student student) {
		return studentServiceCrud.insertStudent(student);
	}
	
	// 수정 
	@PostMapping("/update")
	public Optional<Student> updateStudent(Student student) {
		return studentServiceCrud.updateStudent(student);
	}
	
	// 삭제
	@GetMapping("/delete/{id}")
	public void deleteStudent(@PathVariable("id") Integer id){
		studentServiceCrud.deleteStudent(id);
	}
	
	// 전체 건수
	@GetMapping("/count")
	public long countStudent() {
		return studentServiceCrud.countStudent();
	}
	
	// 페이징 목록
	// pageNumber 첫 페이지는 0이다 
	@GetMapping("/page")
	public List<Student> listStudent(
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("pageSize") Integer pageSize){
		return studentServiceCrud.listStudent(pageNumber, pageSize);
	}
}
