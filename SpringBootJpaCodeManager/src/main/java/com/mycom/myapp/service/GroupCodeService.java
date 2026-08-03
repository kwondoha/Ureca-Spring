package com.mycom.myapp.service;

import com.mycom.myapp.dto.GroupCodeDto;
import com.mycom.myapp.dto.GroupCodeResultDto;

public interface GroupCodeService {
	GroupCodeResultDto insertGroupCode(GroupCodeDto groupCodeDto);
	GroupCodeResultDto updateGroupCode(GroupCodeDto groupCodeDto);
	GroupCodeResultDto deleteGroupCode(String groupCode);
	
	GroupCodeResultDto listGroupCode();
	GroupCodeResultDto detailGroupCode(String groupCode);
}
