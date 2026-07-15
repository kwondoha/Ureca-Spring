package com.mycom.myapp.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // @Rest~ X
public class PageController {

	@GetMapping("/login")
	public String login() {
		return "/login.html";
	}
	
	@GetMapping("/register")
	public String register() {
		return "/register.html";
	}
	
	@GetMapping("/board")
	public String board() {
		return "/board.html";
	}
}
