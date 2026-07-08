package com.mycom.myapp.service;

import java.util.List;

import com.mycom.myapp.entity.Student;

public interface StudentService {
	List<Student> findAll();
	Student findById(int id);
	Student save(Student student);
}
