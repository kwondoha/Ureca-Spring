package com.mycom.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycom.myapp.entity.Student;
import com.mycom.myapp.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService{

	private final StudentRepository studentRepository;
	
	@Override
	public List<Student> findAll() {
		
		log.debug("findAll() debug");
		log.info("findAll() info");
		return studentRepository.findAll();
	}

	@Override
	public Student findById(int id) {
		return studentRepository.findById(id).orElse(null);
	}

	@Override
	public Student save(Student student) {
		return studentRepository.save(student);
	}

}
