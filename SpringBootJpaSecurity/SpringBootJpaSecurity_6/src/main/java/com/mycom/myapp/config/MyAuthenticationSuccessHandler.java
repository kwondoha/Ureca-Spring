package com.mycom.myapp.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Spring Security 의 /login 에 의한 판정
// 로그인 성공하면 .successHandler(이 클래스의 객체) 에 의해 처리 위임
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// Response 로 성공 result:success json 응답
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		
		String jsonStr = """
				{"result":"success"}
				""";
		response.getWriter().write(jsonStr);
	}

}
