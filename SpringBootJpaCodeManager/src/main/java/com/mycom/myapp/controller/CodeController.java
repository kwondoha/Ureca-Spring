package com.mycom.myapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.dto.CodeDto;
import com.mycom.myapp.dto.CodeResultDto;
import com.mycom.myapp.service.CodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CodeController {

	private final CodeService CodeService;

	// 목록
	@GetMapping("/codes")
	public CodeResultDto listCode(@RequestParam("groupCode") String groupCode) {
		return CodeService.listCode(groupCode);
	}

	// 상세
	@GetMapping("/codes/{groupCode}/{code}")
	public CodeResultDto detailCode(@PathVariable("groupCode") String groupCode, @PathVariable("code") String code) {
		CodeDto codeDto = CodeDto.builder().groupCode(groupCode).code(code).build();
		return CodeService.detailCode(codeDto);
	}

	// 등록
	@PostMapping("/codes")
	public CodeResultDto insertCode(@RequestBody CodeDto codeDto) { // 클라이언트에서 json 요청
		return CodeService.insertCode(codeDto);
	}

	// 수정
	@PutMapping("/codes/{groupCode}/{code}")
	public CodeResultDto updateCode(
			@PathVariable("groupCode") String groupCode,
			@PathVariable("code") String code,
			@RequestBody CodeDto codeDto) { // 클라이언트에서 json 요청
		codeDto.setCode(code);
		return CodeService.updateCode(codeDto);
	}

	// 삭제
	@DeleteMapping("/codes/{groupCode}/{code}")
	public CodeResultDto deleteCode(@PathVariable("groupCode") String groupCode, @PathVariable("code") String code) {
		CodeDto codeDto = CodeDto.builder().groupCode(groupCode).code(code).build();
		return CodeService.deleteCode(codeDto);
	}
}
