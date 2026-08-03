package com.mycom.myapp.dto;

import com.mycom.myapp.entity.GroupCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupCodeDto {
	private String groupCode;
	private String groupCodeName;
	private String groupCodeDesc;
	
	// Entity -> Dto
	public static GroupCodeDto fromGroupCode(GroupCode groupCode) {
		return GroupCodeDto.builder()
				.groupCode(groupCode.getGroupCode())
				.groupCodeName(groupCode.getGroupCodeName())
				.groupCodeDesc(groupCode.getGroupCodeDesc())
				.build();
	}
}