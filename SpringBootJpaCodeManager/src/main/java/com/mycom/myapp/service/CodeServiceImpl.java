package com.mycom.myapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycom.myapp.dto.CodeDto;
import com.mycom.myapp.dto.CodeResultDto;
import com.mycom.myapp.entity.Code;
import com.mycom.myapp.entity.key.CodeKey;
import com.mycom.myapp.repository.CodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService{

	private final CodeRepository codeRepository;
	
	@Override
	public CodeResultDto insertCode(CodeDto codeDto) {
		CodeResultDto resultDto = new CodeResultDto();
		
		try {
			Code code = Code.fromCodeDto(codeDto);
			code.setNew(true);
			codeRepository.save(code);
			resultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

	@Override
	public CodeResultDto updateCode(CodeDto codeDto) {
		CodeResultDto resultDto = new CodeResultDto();
		
		try {
			CodeKey codeKey = new CodeKey(codeDto.getGroupCode(), codeDto.getCode()); // Code 의 key
			Optional<Code> optionalCode = codeRepository.findById(codeKey);
			
			if( optionalCode.isPresent()) {
				Code code = optionalCode.get(); // 영속화된 Code 객체
				code.setCodeName(codeDto.getCodeName());
				code.setCodeNameBrief(codeDto.getCodeNameBrief());
				code.setOrderNo(codeDto.getOrderNo());
				code.setNew(false); // update
				codeRepository.save(code);
				resultDto.setResult("success");
			}else {
				resultDto.setResult("fail");
			}
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

	@Override
	public CodeResultDto deleteCode(CodeDto codeDto) {
		CodeResultDto resultDto = new CodeResultDto();
		
		try {
			CodeKey codeKey = new CodeKey(codeDto.getGroupCode(), codeDto.getCode());
			codeRepository.deleteById(codeKey);
			resultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

	@Override
	public CodeResultDto listCode(String groupCode) {
		CodeResultDto resultDto = new CodeResultDto();
		
		try {
			List<CodeDto> codeDtoList = new ArrayList<>();
			codeRepository.findByGroupCode(groupCode)
				.forEach(
					code -> codeDtoList.add(
								CodeDto.fromCode(code)
							)
						);
			
			resultDto.setCodeDtoList(codeDtoList);
			resultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

	@Override
	public CodeResultDto detailCode(CodeDto codeDto) {
		CodeResultDto resultDto = new CodeResultDto();
		
		try {
			CodeKey codeKey = new CodeKey(codeDto.getGroupCode(), codeDto.getCode()); // Code 의 key
			Optional<Code> optionalCode = codeRepository.findById(codeKey);
			
			if( optionalCode.isPresent()) {
				Code detailCode = optionalCode.get(); // 영속화된 Code 객체
				resultDto.setCodeDto(CodeDto.fromCode(detailCode));
				resultDto.setResult("success");
			}else {
				resultDto.setResult("fail");
			}
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

}