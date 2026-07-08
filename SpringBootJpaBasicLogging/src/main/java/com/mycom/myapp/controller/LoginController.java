package com.mycom.myapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
	
	@PostMapping("/auth/login")
	public String login(
			@RequestParam("username") String username, 
			@RequestParam("password") String password,
			HttpSession session
	) {
		// 검증 X
		session.setAttribute("username", username);
		return """
				{"result":"success"}
				""";
	}
}
