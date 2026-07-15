package com.mycom.myapp.jwt;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtController {

	private final JwtUtil jwtUtil;

	@PostMapping("/token")
	public String testToken(HttpServletRequest request) {
		return jwtUtil.getTokenFromHeader(request);
	}
}