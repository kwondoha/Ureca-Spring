package com.mycom.myapp.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc // @SpringBootTest 환경에서 MockMvc를 사용할 수 있도록 
public class SpringSecurityTest {

	// SpringSecurity의 마지막 #4 설정 기준
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("get / - permitAll 확인")
	void index_permitAll() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("get /hello - authenticated 확인")
	void hello_authenticated() throws Exception{
		mockMvc.perform(get("/hello"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	@DisplayName("get /hello - user authenticated 확인")
	void hello_user_authenticated() throws Exception{
		mockMvc.perform(get("/hello").with(user("user")))
		.andExpect(status().isOk());
	}
}
