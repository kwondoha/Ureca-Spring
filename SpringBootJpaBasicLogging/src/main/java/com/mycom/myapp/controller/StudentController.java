package com.mycom.myapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.entity.Student;
import com.mycom.myapp.service.StudentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
	private final StudentService studentService;
	
	// 목록
	@GetMapping("/students")
	public List<Student> list(){

		// 테스트용 logging
		log.trace("list() trace");
		log.debug("list() debug");
		log.info("list() info");
		log.warn("list() warn");
		log.error("list() error");

		return studentService.findAll();
	}
	
	// 상세
	@GetMapping("/students/{id}")
	public Student detail(@PathVariable("id") Integer id) {
		return studentService.findById(id);
	}
	
	// 등록
	@PostMapping("/students")
	public Student insert(@RequestBody StudentDto studentDto) {
		Student student = Student.builder()
							.name(studentDto.getName())
							.email(studentDto.getEmail())
							.phone(studentDto.getPhone())
							.build();
		
		return studentService.save(student);
	}
}





