package com.mycom.myapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;
import com.mycom.myapp.entity.Student;
import com.mycom.myapp.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceCrudImpl implements StudentServiceCrud{
	
	// StudentRepository 구현한 객체 DI - 생성자
	private final StudentRepository studentRepository;

	@Override
	public StudentResultDto listStudent() {
		StudentResultDto studentResultDto = new StudentResultDto();
		try {
			List<Student> studentList = studentRepository.findAll(); // 전체 목록
			List<StudentDto> studentDtoList = new ArrayList<>();
			
			// Student => StudentDto
			// Student Entity 는 Service, Repository Layer 사용
			// StudentDto 는 Controller, Service Layer 주로 사용
			studentList.forEach( student -> {
				StudentDto studentDto = StudentDto.builder()
											.id(student.getId())
											.name(student.getName())
											.email(student.getEmail())
											.phone(student.getPhone())
											.build();
				studentDtoList.add(studentDto);
			});
			
			studentResultDto.setList(studentDtoList);
			studentResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;
	}

	@Override
	public StudentResultDto detailStudent(int id) {
		
		StudentResultDto studentResultDto = new StudentResultDto();
		try {
			Optional<Student> optionalStudent = studentRepository.findById(id); // id 에 의한 상세

			optionalStudent.ifPresentOrElse(
					
					student -> {
						StudentDto studentDto = StudentDto.builder()
								.id(student.getId())
								.name(student.getName())
								.email(student.getEmail())
								.phone(student.getPhone())
								.build();	
						studentResultDto.setDto(studentDto);
						studentResultDto.setResult("success");
					},
					()->{
						studentResultDto.setResult("fail");
					}
			);

		}catch(Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;
	}

	@Override
	public StudentResultDto insertStudent(StudentDto studentDto) {
		
		StudentResultDto studentResultDto = new StudentResultDto();
		
		// StudentDto -> Student
		Student student = Student.builder()
							.name(studentDto.getName())
							.email(studentDto.getEmail())
							.phone(studentDto.getPhone())
							.build();
		
		try {
			studentRepository.save(student);  // 등록
			studentResultDto.setResult("success");
			
		}catch(Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;
	}

	@Override
	public StudentResultDto updateStudent(StudentDto studentDto) {
		
		StudentResultDto studentResultDto = new StudentResultDto();
		
		// StudentDto -> Student
		Student student = Student.builder()
							.id(studentDto.getId())   // id O
							.name(studentDto.getName())
							.email(studentDto.getEmail())
							.phone(studentDto.getPhone())
							.build();
		try {
			studentRepository.save(student);  // 등록
			studentResultDto.setResult("success");
			
		}catch(Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;		

	}

	@Override
	public StudentResultDto deleteStudent(int id) {
		StudentResultDto studentResultDto = new StudentResultDto();

		try {
			studentRepository.deleteById(id);
			studentResultDto.setResult("success");
			
		}catch(Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;	
	}

	@Override
	public StudentResultDto countStudent() {
		StudentResultDto studentResultDto = new StudentResultDto();

		try {
			Long count = studentRepository.count(); // 전체 건수
			studentResultDto.setCount(count);
			studentResultDto.setResult("success");
			
		}catch(Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;			
	}

	@Override
	public StudentResultDto listStudent(int pageNumber, int pageSize) {
		
		StudentResultDto studentResultDto = new StudentResultDto();
		try {
			// 페이징 목록
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			Page<Student> page = studentRepository.findAll(pageable);
			
			List<StudentDto> studentDtoList = new ArrayList<>();

			page.toList().forEach( student -> {
				StudentDto studentDto = StudentDto.builder()
											.id(student.getId())
											.name(student.getName())
											.email(student.getEmail())
											.phone(student.getPhone())
											.build();
				studentDtoList.add(studentDto);
			});
			
			studentResultDto.setList(studentDtoList);
			studentResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;
	}

}