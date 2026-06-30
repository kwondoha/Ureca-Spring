package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// HTML 페이지 요청 대응
// Web Server <-> Web Application Server
//   .html           @GetMapping(~~)
// 단순하게 .html 에 대한 응답만 존재 <= Controller 매핑은 비효율적이다.
// 회원의 다양한 접근 로그 등 목적이 있는 Controller 매핑은 적절하다.
@Controller
public class PageController {

	// 아래 url 매핑이 없으면 기본 / 에 대해 index.html 을 찾는다. 없으면 오류
	@GetMapping("/")
	public String home() {
		return "home.html";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	
	// post 요청에 대해 view resolver 를 통한 html 응답 처리 X
	// redirect 처리 필요
	@PostMapping("/login")
	public String loginResult(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println(username);
		System.out.println(password);
		return "redirect:/main.html";
	}
	
	@GetMapping("/login2")
	public String login2() {
		return "login2.html";
	}
	
	// fetch 에 대응하는 data 요청, 응답
	@PostMapping("/login2")
	@ResponseBody
	public Map<String, String> login2Result(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println(username);
		System.out.println(password);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}	
}