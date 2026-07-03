package com.mycom.myapp.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserResultDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;

	@PostMapping("/login")
	public UserResultDto login(
			@RequestParam("email") String email, 
			@RequestParam("password") String password,
			HttpSession session) {
		
		UserResultDto userResultDto = loginService.login(email, password);
		
		// 로그인에 성공한 경우 session에 userDto 객체를 담는다 
		if(userResultDto.getResult().equals("success")) {
			session.setAttribute("userDto", userResultDto.getUserDto());
		}
		
		return userResultDto;
	}
	
	@GetMapping("/logout")
	public UserResultDto logout(HttpSession session) {
		// logout도 별도의 비즈니스로직이 있다면 service - repository를 통해서 구현 
		UserResultDto userResultDto = new UserResultDto();
		
		try {
			session.invalidate();
			userResultDto.setResult("success");
		} catch(Exception e) {
			e.printStackTrace();
			userResultDto.setResult("fail");
		}
		
		return userResultDto;
	}
}
