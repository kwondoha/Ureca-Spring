package com.mycom.myapp.basic;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// 본격적인 테스트 모음
// 우리가 원하는 단순 출력X 예외 발생 상황 X
// 비즈니스 로직을 구현, 테스트하는 것이 목적 -> 내가 원하는 것과 실제로 처리된 결과 비교, 검증 assert

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAssert2 {

	// 예외 발생
	int getStringLength(String str) { return str.length(); }
	
	// assertThrows의 주요한 활용은 사용자 정의 예외 발생 확인하는 테스트 
	@Test
	@Order(1)
	void test1() {
//		String str = "hello";
		String str = null;
//		assertThrows(NullPointerException.class, getStringLength(str), "NullPointerException이 발생되어야 한다.");
// 		lamda를 사용해야 함.
		assertThrows(NullPointerException.class, () -> getStringLength(str), "NullPointerException이 발생되어야 한다.");
	}
	
	// 묶음 (그룹) 테스트
	int result = 0;
	int m1() { return 4;}
	boolean m2() { return false;}
	String m3() { return "hello";}
	
	@Test
	@Order(2)
	void test2(){
		assertAll("묶음 테스트 ",
			() -> assertEquals(4, m1()),
			() -> assertTrue(m2()),
			() -> assertNotNull(m3()),
			() -> assertEquals(0, result)
		);
	}
	
	// 배열 Array
	// int가 아닌 객체 배열인 경우 길이가 같아야 하고, 위치가 같은 객체의 두 equals & hashcode의 결과가 같아야 함 
	int[] expectedArray = {1,2,3};
//	int[] actualArray = {1,2,3};	
//	int[] actualArray = {1,2,3,4};	// 길이가 다르면 실패
	int[] actualArray = {1,2,4};	// 항목이 다르면 실패 
	
	@Test
	@Order(3)
	void test3() {
		assertArrayEquals(expectedArray, actualArray);
	}
	
	// Collection
	List<String> expectedList = List.of("abc", "def");
//	List<String> actualList = List.of("abc", "def");	
//	List<String> actualList = List.of("def", "abc");		// 순서 
//	List<String> actualList = List.of("abc", "def", "xyz"); // 개수 
	List<String> actualList = List.of("abc", new String("def"));

	@Test 
	@Order(4)
	void test4() {
		assertIterableEquals(expectedList, actualList);
	}
	
	// 객체 비교
	@Test 
	@Order(5)
	void test5() {
		String str1 = "Hello";
//		String str2 = str1;
		String str2 = new String("Hello");
		
		assertEquals(str1, str2);
	}
	
	@Test 
	@Order(6)
	void test6() {
		String str1 = "Hello";
//		String str2 = str1;
		String str2 = new String("Hello");
		
		assertSame(str1, str2);	// String Design Pattern 적용 객체 테스트
	}
	
	// 수행 시간 테스
	// 작성한 BL 처리 메소드
	// Service -> Repository 
	void testBL() {
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("testBL()");
	}
	
	@Test
	@Order(7)
	void test7() {
		assertTimeout(Duration.ofSeconds(1), ()->testBL(), "1초 미만 수행 테스트");
	}
}
