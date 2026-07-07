package com.mycom.myapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

// 프로젝트 전체 (전역적으로) 예외 처리자
// 클라이언트 응답 설계 관점에서 ~~ResultDto 와 호환 X
//    <= ~ResultDto 방식은 Controller, Service 에서 try-catch 로 모든 예외를 직접 처리하는 방식
//    <= 아래 방식은 Controller, Service 에서 발생하는 예외를 그대로 위로 던져서 Spring Boot 의 전역 예외 처리자(아래 클래스) 에서 처리 위임
@RestControllerAdvice
public class GlobalExceptionHandler {

	// StudentExceptionHandlingController 의 NullPointerException 
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleNullPointer(
			NullPointerException ex,
			HttpServletRequest request
	){
		ErrorResponse errorResponse = ErrorResponse.builder()
										.timestamp(LocalDateTime.now())
										.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
										.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
										.message(ex.getMessage())
										.path(request.getRequestURI())
										.build();
		System.out.println(ex.getMessage()); // NullPointerException getMessage() 가 null
		System.out.println(errorResponse);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	
	// StudentControllerCrudResponseEntity 와 대응되는 StudentServiceCrudImpl 의 detailStudent() 코드 예외 처리
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleStudentNotFound(
			StudentNotFoundException ex,
			HttpServletRequest request
	){
		ErrorResponse errorResponse = ErrorResponse.builder()
										.timestamp(LocalDateTime.now())
										.status(HttpStatus.NOT_FOUND.value())
										.error(HttpStatus.NOT_FOUND.getReasonPhrase())
										.message(ex.getMessage())
										.path(request.getRequestURI())
										.build();
		System.out.println(ex.getMessage()); // 사용자 정의 예외의 메시지
		System.out.println(errorResponse);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}	
	
	// StudentValidationController 대응
	// Valid 검증 실패 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleStudentNotValid(
			MethodArgumentNotValidException ex,
			HttpServletRequest request
	){
		ErrorResponse errorResponse = ErrorResponse.builder()
										.timestamp(LocalDateTime.now())
										.status(HttpStatus.BAD_REQUEST.value())
										.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
										.message("입력값 검증에 실패했습니다.") // 사용자 정의 메세지
										.path(request.getRequestURI())
										.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}		
}












