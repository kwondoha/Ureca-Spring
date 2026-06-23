package com.mycom.myapp.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;


// data 요청만
@ControllerAdvice
@ResponseBody // json 응답
public class GlobalExceptionHandler {

	// 데이터 요청에 대한 오류 예외 처리
	// 아래 예외처리가 다른 데이터 요청 컨트롤러 모두에게 일괄적용한다면 한곳에서 처리하는 게 더 효율적
	@ExceptionHandler(Exception.class) // 이 컨트롤러에서 발생하는 모든 예외는 이곳에서 처리 
	public Map<String, String> pageExceptionHandler(HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("result", "fail");
		return map;
	}
	
}
