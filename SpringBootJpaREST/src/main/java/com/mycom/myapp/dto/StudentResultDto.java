package com.mycom.myapp.dto;

import java.util.List;

import lombok.Data;

// Student 비즈니스로직 처리 응답을 위한 Dto, 프론트와 소통을 위한 Dto
// 설계에 따라 다양항 형태를 취한다.
// Spring Boot 프로젝트의 응답이 항상 ~ ResultDto 만 있는 건 아니다. <= 한국 스타일
// 정상 : Raw Data, 실패 : Spring Boot Error 포맷
@Data
public class StudentResultDto {
	private String result;
	private StudentDto dto;
	private List<StudentDto> list;
	private Long count;	
}