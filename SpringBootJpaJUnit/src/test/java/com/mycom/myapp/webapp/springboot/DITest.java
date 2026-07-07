package com.mycom.myapp.webapp.springboot;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycom.myapp.user.controller.UserController;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.service.UserService;

// SpringBoot 모든 것을 테스트 대상
// @SpringBootTest <- 전체 Spring Context 가동, 실제 Bean DI (무겁다 모든 테스트 가능)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DITest {

	// user의 Controller, Service, Repository DI 테스트
	// 스프링 프로젝트 개발 시 Repository -> Service -> Controller 순서대로 개발하며 단위테스트 JUnit을 함께 진행해야 한다
	@Autowired
	UserController userController;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	@Order(1)
	void testDI() {
		assertNotNull(userController);
		assertNotNull(userService);
		assertNotNull(userRepository);
	}
}
