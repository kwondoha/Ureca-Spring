package com.mycom.myapp.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Spring Security 의 /login 에 의한 판정
// 로그인 실패하면 .failureHandler(이 클래스의 객체) 에 의해 처리 위임
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		// Response 로 실패 result:fail json 응답
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		
		String jsonStr = """
				{"result":"fail"}
				""";
		response.getWriter().write(jsonStr);
		
	}



}
