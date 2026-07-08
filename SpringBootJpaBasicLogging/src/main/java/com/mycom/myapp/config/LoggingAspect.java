package com.mycom.myapp.config;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 필수 Logging 은 개발자의 손에 의해서 X => AOP
// 이 Logging Aspect 의 시나리오는 repository layer call 에 대한 요청은 어떤 로그인 사용자가 호출했는 지 logging
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

	// 세션 DI
	private final HttpSession session;
	
	@Pointcut("execution ( * com.mycom.myapp.repository.*.*(..) )")
	private void repositoryMethod() {}
	
	@Before("repositoryMethod()")
	public void logRepositoryCall(JoinPoint joinPoint) {
		log.info("LoggingAspect - logRepositoryCall");
		// 로그인 사용자 X : logging 없이 return
		// 로그인 사용자 O : username, methodName, 시각
		String username = (String) session.getAttribute("username");
		if( username == null) return;
		
		String methodName = joinPoint.getSignature().getName();
		log.info("User [{}] called method {} at {}", username, methodName, LocalDateTime.now());
	}
}




