package com.mycom.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
