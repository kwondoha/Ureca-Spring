package com.mycom.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mycom.myapp.entity.Student;
import com.mycom.myapp.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceCrudImpl implements StudentServiceCrud{
	
	// StudentRepository 구현한 객체 DI - 생성자
	private final StudentRepository studentRepository;

	@Override
	public List<Student> listStudent() {
		return studentRepository.findAll(); // 전체 목록
	}

	@Override
	public Optional<Student> detailStudent(int id) {
		return studentRepository.findById(id);  // id 에 의한 상세
	}

	// CrudRepository 의 save() 처리 방식
	// save() 에 전달되는 student 객체의 id 항목이 없으면 -> 등록 -> persist()  - insert
	// save() 에 전달되는 student 객체의 id 항목이 있으면 -> 수정 -> merge() - select + update
	
	// id가 있는 상태에서 save
	// service insertStudent() -> studentRepository.save()
	@Override
	public Student insertStudent(Student student) {
		return studentRepository.save(student);  // 등록
	}

	@Override
	public Optional<Student> updateStudent(Student student) {
		return Optional.of(studentRepository.save(student)); // 수정
	}

	@Override
	public void deleteStudent(int id) {
		studentRepository.deleteById(id); // 삭제
	}

	@Override
	public long countStudent() {
		return studentRepository.count(); // 전체 건수
	}

	@Override
	public List<Student> listStudent(int pageNumber, int pageSize) {
		// 페이징 목록
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Student> page = studentRepository.findAll(pageable);
		return page.toList();
	}

}