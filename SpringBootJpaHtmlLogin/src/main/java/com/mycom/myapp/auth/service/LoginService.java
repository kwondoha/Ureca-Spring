package com.mycom.myapp.auth.service;

import com.mycom.myapp.user.dto.UserResultDto;

// 로그인 비즈니스 로직을 처리
public interface LoginService {
	UserResultDto login(String email, String password);
}
