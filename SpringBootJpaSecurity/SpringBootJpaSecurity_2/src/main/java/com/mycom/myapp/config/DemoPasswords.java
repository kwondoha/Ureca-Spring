package com.mycom.myapp.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 고정된 패스워드 관리
public class DemoPasswords {
	public static final String PLAIN = "1234";
	public static final String ENCODED = new BCryptPasswordEncoder().encode(PLAIN);
}
