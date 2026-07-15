package com.mycom.myapp.user.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 프론트에서 csrf token 요청에 대응
// 요청하는 클라이언트 브라우저가 최초 접속했을 때 CsrfFilter 가 만들어서 쿠키로 내려보낸 token 값고 동일한 것을 응답
@RestController
public class CsrfController {

	@GetMapping("/csrf-token")
	public CsrfToken csrf(CsrfToken token) {
		token.getToken();
		return token;
	}
}
