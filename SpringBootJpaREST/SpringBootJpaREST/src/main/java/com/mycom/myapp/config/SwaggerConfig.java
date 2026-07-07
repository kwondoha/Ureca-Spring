package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI openApi() {
		return new OpenAPI() // OpenAPI 문서 객체
				.components(new Components())
				.info(apiInfo());
	}

	private Info apiInfo() { // Info Swagger UI 문서 최상단에 표시되는 API 정보
		return new Info()
				.title("학생 관리 API")
				.description("REST API로 구현된 학생 관리 기능 테스트")
				.version("v0.9");
	}
}
