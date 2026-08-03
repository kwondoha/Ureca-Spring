package com.mycom.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.mycom.myapp.dto.CodeDto;
import com.mycom.myapp.dto.CommonCodeResultDto;
import com.mycom.myapp.entity.Code;
import com.mycom.myapp.repository.CommonCodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService{

	private final CommonCodeRepository commonCodeRepository;
	
	@Override
	public CommonCodeResultDto getCommonCodeList(List<String> groupCodes) {
		CommonCodeResultDto resultDto = new CommonCodeResultDto();
		
		try {
			List<Code> codeList = commonCodeRepository.findByGroupCodes(groupCodes); // 전달된 groupCode 들에 의해 복수개의 groupCode 에 해당하는 Code 가 여러개 함께
			Map<String, List<CodeDto>> commonCodeDtoListMap = new HashMap<>();
			
			// codeList 분할 작업
			String currGroupcode = ""; // 현재 처리 중인 그룹코드
			List<CodeDto> codeDtoList = null; // 현재 그룹의 코드dto 누적 리스트
			
			for (Code code : codeList) {
				
				String groupCode = code.getCodeKey().getGroupCode();
				
				// 그룹 코드가 바뀌면 => 이전 그룹 결과를 Map 에 put 하고 새 리스트 시작
				// 최초 진입 ( currGroupcode = "" ) 도 아래 if 해당
				if( ! currGroupcode.equals(groupCode) ) {
					
					// 최초 진입 ( currGroupcode = "" ) 이 아닐 때만 put
					if( Strings.isNotEmpty(currGroupcode)) {
						commonCodeDtoListMap.put(currGroupcode, codeDtoList);
					}
					
					// 새로운 그룹코드의 시작, 새로운 List 자료구조 초기화
					currGroupcode = groupCode;
					codeDtoList = new ArrayList<>();
				}
				
				codeDtoList.add(CodeDto.fromCode(code));				
			}
			
			// 반복문이 끝나면 마지막 그룹도 Map 추가
			commonCodeDtoListMap.put(currGroupcode, codeDtoList);
			
			resultDto.setCommonCodeDtoListMap(commonCodeDtoListMap);
			resultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

}