package com.mycom.myapp.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // 모든 테스트가 하나의 인스턴스를 공유
@AutoConfigureMockMvc
@Slf4j
public class JwtUtilTest {

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	private final String username = "dskim";
	private final List<String> roles = List.of("ROLE_CUSTOMER", "ROLE_ADMIN");
	private String token; // 개별 테스트에서 공유할 예정 <= test 1번에서 token 생성, test 2번에서 token 사용....JUnit 5 기준 test 마다 JwtUtilTest 객체를 각각 생성...
	
	@Test
	@Order(1)
	void testDI() {
		assertNotNull(jwtUtil);
		assertNotNull(jwtUtil.getSecretKey());  // @Getter 를 통한
		log.debug("[1 단계] JwtUtil DI 성공, 알고리즘 : {}", jwtUtil.getSecretKey().getAlgorithm());
	}
	
	@Test
	@Order(2)
	void testCreateToken() {
		token = jwtUtil.createToken(username, roles);
		assertNotNull(token);
		assertEquals(username, jwtUtil.getUsernameFromToken(token));
		log.debug("[2 단계] createToken, username = {}, roles={}", username, roles);
		log.debug("[2 단계] token = {}", token);
		log.debug("[2 단계] getUsernameFromToken = {}", jwtUtil.getUsernameFromToken(token));
	}	
	
	@Test
	@Order(3)
	void testGetTokenFromHeader() throws Exception{
		assertNotNull(token); // test 2 에서 생성한 토큰
		String responseBody = mockMvc.perform( post("/token").header("X-AUTH-TOKEN", token)  )
								.andExpect(status().isOk())
								.andExpect(content().string(token))
								.andReturn()
								.getResponse()
								.getContentAsString();
		log.debug("[3 단계] responseBody = {}", responseBody);
	}		
}

