package com.mycom.myapp.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 별도의 처리가 없으면 Security가 authenticated면 로그인 페이지로 redirect
// SecurityConfig에서 permitAll()
@RestController
public class WellKnownController {

	@GetMapping("/.well-known/**")
	public ResponseEntity<Void> wellKnown() {
		return ResponseEntity.noContent().build();
	}
}
