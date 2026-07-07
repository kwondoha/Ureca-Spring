package com.mycom.myapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 예외 발생시키는 Controller 
@RestController
@RequestMapping("/api/exceptions")
public class StudentExceptionHandlingController {
	
	// 아무런 예외 대응 설정, 코드를 작성하지 않은 경우
	// 1. 페이지 요청 (브라우저) => Whitelabel Error Page
	// 2. 데이터 요청 (postman, fetch) => JSON format 응답
/*
		 {
		    "timestamp": "2026-07-06T04:14:11.547Z",
		    "status": 500,
		    "error": "Internal Server Error",
		    "trace": "java.lang.NullPointerException..."
		    "message": "No message available",
		    "path": "/api/exceptions/null-exception"
		}    	
 */
	@GetMapping("/null-exception")
	public void nullException() {
		throw new NullPointerException();
		// String s = null; s.length()
	}
}
