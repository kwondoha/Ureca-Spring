package com.mycom.myapp.exception;

// Service Layer 에서 있어야 할 Student 가 없은 경우를 표현
public class StudentNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public StudentNotFoundException(int id) {
		// 상위 예외의 생성자 super(message)
		super("학생을 찾을 수 없습니다. id=" + id);
	}
}
