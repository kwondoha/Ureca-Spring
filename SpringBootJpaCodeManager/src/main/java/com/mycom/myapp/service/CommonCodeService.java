package com.mycom.myapp.service;

import java.util.List;

import com.mycom.myapp.dto.CommonCodeResultDto;

public interface CommonCodeService {
	CommonCodeResultDto getCommonCodeList(List<String> groupCodes);
}