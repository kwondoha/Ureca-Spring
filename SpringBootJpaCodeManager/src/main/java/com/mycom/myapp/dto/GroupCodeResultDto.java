package com.mycom.myapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class GroupCodeResultDto {
	private String result;
	private GroupCodeDto groupCodeDto; // 단건
	private List<GroupCodeDto> groupCodeDtoList; // 여러 건
}