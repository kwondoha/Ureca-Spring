package com.mycom.myapp.basic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// JUnit 6 version 
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order 사용 가능
public class TestBasic {

	@Test
	@Order(2)
	void test1() {
		// 테스트 코드 위치 
		System.out.println("test1()");
	}
	
	@Test
	@Order(1)
	void test2() {
		// 테스트 코드 위치 
		System.out.println("test2()");
	}
	
	@Test
	@Order(3)
	@DisplayName("회원 등록 테스트") // junit 결과 dash board에 method 이름 대신 표현할 문자열
	void test3() {
		// 테스트 코드 위치 
		System.out.println("test3()");
	}
	
	// Test의 결과
	// 성공 : 에러 없이 원하는 결과 
	// Error : 테스트 도중 예외 발생 <- 일반적인 버그 해결 후에 다시 테스트 
	// 실패 : 에러 없으나 원하는 결과 X 
	
	@Test
	@Order(4)
	@DisplayName("예외 테스트")
	void test4() {
		String s = null;
		s.length();
		
		// 테스트 코드 위치 
		System.out.println("test4()");
	}
	
	// 테스트 시나리오 중 초기화, 정리 
	// @BeforeAll @AfterAll <- 전체 테스트 수행 중 맨 앞 1번, 맨 뒤 1번 각각 수행
	// @Test가 없으므로 테스트 대상 아님 -> 대시보드에 없음
	@BeforeAll
	static void beforeAll() {
		// 전체 테스트 전 사전 작업, 초기화 등
		System.out.println("beforeAll()");
	}
	
	@AfterAll
	static void afterAll() {
		// 전체 테스트 후 정리 작업, 리소스 반납
		System.out.println("afterAll()");
	}
	
	@BeforeEach
	void beforeEach() {
		// 전체 테스트 전 사전 작업, 초기화 등
		System.out.println("beforeEach()");
	}
	
	@AfterEach
	void afterEach() {
		// 전체 테스트 후 정리 작업, 리소스 반납
		System.out.println("afterEach()");
	}
}
