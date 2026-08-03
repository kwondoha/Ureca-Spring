package com.mycom.myapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class CodeResultDto {
	private String result;
	private CodeDto CodeDto; // 단건
	private List<CodeDto> codeDtoList; // 여러 건
}
