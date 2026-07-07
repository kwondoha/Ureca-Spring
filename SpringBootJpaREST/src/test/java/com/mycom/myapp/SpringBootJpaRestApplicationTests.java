package com.mycom.myapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// SpringBoot의 빌드도구들은 빌드과정에서 Test 단계가 있음을 가정. 없으면 빌드 오류 발생
// 만약 Test모듈 없이 빌드를 진행하려면 gradle -x build 옵션 필요
// 모든 SpringBoot 프로젝트는 반드시 Test 모듈을 작성해야 한다
@SpringBootTest
class SpringBootJpaRestApplicationTests {

//	@Test
//	void contextLoads() {
//	}

}
