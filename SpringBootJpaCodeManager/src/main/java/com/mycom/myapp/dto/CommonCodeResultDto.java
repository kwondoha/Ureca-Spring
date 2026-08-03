package com.mycom.myapp.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

// 공통코드를 업무 도메인에서 사용하는 코드
// 공통코드 CRUD 관리자용 X
// 프론트에서 회원구분, 회원등급, ... 복수 개의 공통코드 및 하위 코드 요청에 대한 응답
//  위 1 번의 요청에 여러 개의 코드 그룹이 응답
@Data
public class CommonCodeResultDto {
	private String result;
	private Map<String, List<CodeDto>> commonCodeDtoListMap;
}