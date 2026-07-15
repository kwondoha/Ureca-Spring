package com.mycom.myapp.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.auth.dto.LoginResultDto;
import com.mycom.myapp.auth.service.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	@PostMapping("/login")
	public LoginResultDto login(@RequestParam("username") String username,
								@RequestParam("password") String password) {
		return loginService.login(username, password);
	}
}
