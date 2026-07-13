package com.mycom.myapp.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 고정 (final) 된 패스워드 관리
public final class DemoPasswords {
	public static final String PLAIN = "1234";
	public static final String ENCODED = new BCryptPasswordEncoder().encode(PLAIN);
}
