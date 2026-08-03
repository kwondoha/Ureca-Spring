package com.mycom.myapp.service;

import com.mycom.myapp.dto.CodeDto;
import com.mycom.myapp.dto.CodeResultDto;

public interface CodeService {
	CodeResultDto insertCode(CodeDto codeDto);
	CodeResultDto updateCode(CodeDto codeDto);
	CodeResultDto deleteCode(CodeDto codeDto); // groupCode, code 2개 컬럼 대응
	
	CodeResultDto listCode(String groupCode);  // 전체 코드 목록이 아닌 groupCode 가 일치하는 코드 목록
	CodeResultDto detailCode(CodeDto codeDto); // groupCode, code 2개 컬럼 대응
}