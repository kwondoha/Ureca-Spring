package com.mycom.myapp.dto;

import com.mycom.myapp.entity.Code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeDto {

	private String groupCode;
	private String code;
	private String codeName;
	private String codeNameBrief;
	private int orderNo;
	
	// Entity -> Dto
	public static CodeDto fromCode(Code code) {
		return CodeDto.builder()
				.groupCode(code.getCodeKey().getGroupCode())
				.code(code.getCodeKey().getCode())
				.codeName(code.getCodeName())
				.codeNameBrief(code.getCodeNameBrief())
				.orderNo(code.getOrderNo())
				.build();
	}
}
