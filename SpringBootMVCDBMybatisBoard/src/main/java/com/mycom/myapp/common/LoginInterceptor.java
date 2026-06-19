package com.mycom.myapp.common;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.myapp.user.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	// 통과 여부를 true, false 로 리턴
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("LoginInterceptor >> preHandle");
		
		// 요청 request 가 로그인 된 요청 <= session 에서 확인 <= request 의 session
		// 로그인 된 요청은 통과
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
		// 거절
		if(userDto == null) {
			System.out.println("LoginInterceptor >> preHandle : 로그인 페이지로 이동");
			response.sendRedirect("/pages/login");
			return false;
		}
		
		// 통과
		return true;
	}
}
