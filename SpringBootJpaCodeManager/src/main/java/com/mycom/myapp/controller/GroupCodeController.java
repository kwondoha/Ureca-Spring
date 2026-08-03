package com.mycom.myapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.dto.GroupCodeDto;
import com.mycom.myapp.dto.GroupCodeResultDto;
import com.mycom.myapp.service.GroupCodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GroupCodeController {

	private final GroupCodeService groupCodeService;
	
	// 목록
	@GetMapping("/groupcodes")
	public GroupCodeResultDto listGroupCode() {
		return groupCodeService.listGroupCode();
	}
	
	// 상세
	@GetMapping("/groupcodes/{groupCode}")
	public GroupCodeResultDto detailGroupCode(@PathVariable("groupCode") String groupCode) {
		return groupCodeService.detailGroupCode(groupCode);
	}
	
	// 등록
	@PostMapping("/groupcodes")
	public GroupCodeResultDto insertGroupCode(@RequestBody GroupCodeDto groupCodeDto) { // 클라이언트에서 json 요청
		return groupCodeService.insertGroupCode(groupCodeDto);
	}
	
	// 수정
	@PutMapping("/groupcodes/{groupCode}")
	public GroupCodeResultDto updateGroupCode(@PathVariable("groupCode") String groupCode, @RequestBody GroupCodeDto groupCodeDto) { // 클라이언트에서 json 요청
		groupCodeDto.setGroupCode(groupCode);
		return groupCodeService.updateGroupCode(groupCodeDto);
	}
	
	// 삭제
	@DeleteMapping("/groupcodes/{groupCode}")
	public GroupCodeResultDto deleteGroupCode(@PathVariable("groupCode") String groupCode) {
		return groupCodeService.deleteGroupCode(groupCode);
	}
}




