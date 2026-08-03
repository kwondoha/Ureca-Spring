package com.mycom.myapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mycom.myapp.dto.GroupCodeDto;
import com.mycom.myapp.dto.GroupCodeResultDto;
import com.mycom.myapp.entity.GroupCode;
import com.mycom.myapp.repository.GroupCodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupCodeServiceImpl implements GroupCodeService{

	private final GroupCodeRepository groupCodeRepository;
	
	@Override
	public GroupCodeResultDto insertGroupCode(GroupCodeDto groupCodeDto) {
		GroupCodeResultDto resultDto = new GroupCodeResultDto();
		
		try {
			GroupCode groupCode = GroupCode.fromGroupCodeDto(groupCodeDto);
			// insert 를 명시, id 가 있지만 JPA 에게 새로운 영속화 되지 않은 객체임을 표시
			groupCode.setNew(true);
			groupCodeRepository.save(groupCode);
			resultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

	@Override
	public GroupCodeResultDto updateGroupCode(GroupCodeDto groupCodeDto) {
		GroupCodeResultDto resultDto = new GroupCodeResultDto();
		
		try {
			// groupCode 로 id 여부 확인, 있을 때에만 update
			Optional<GroupCode> optionalGroupCode = groupCodeRepository.findById(groupCodeDto.getGroupCode());
			
			if( optionalGroupCode.isPresent() ) { // id 가 존재
//				GroupCode groupCode = GroupCode.fromGroupCodeDto(groupCodeDto); // GroupCode 객체가 영속화 X
				GroupCode groupCode = optionalGroupCode.get(); // 영속화 O
				groupCode.setGroupCodeName(groupCodeDto.getGroupCodeName());
				groupCode.setGroupCodeDesc(groupCodeDto.getGroupCodeDesc());
				groupCode.setNew(false);
				groupCodeRepository.save(groupCode);
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
	public GroupCodeResultDto deleteGroupCode(String groupCode) {
		GroupCodeResultDto resultDto = new GroupCodeResultDto();
		
		try {
			groupCodeRepository.deleteById(groupCode);
			resultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

	@Override
	public GroupCodeResultDto listGroupCode() {
		GroupCodeResultDto resultDto = new GroupCodeResultDto();
		
		try {
			List<GroupCodeDto> groupCodeDtoList = new ArrayList<>();
			groupCodeRepository
				.findAll(Sort.by("groupCode"))
				.forEach(
						groupCode -> groupCodeDtoList.add(
								GroupCodeDto.fromGroupCode(groupCode)
						)
				);
			resultDto.setGroupCodeDtoList(groupCodeDtoList);
			resultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult("fail");
		}
		
		return resultDto;
	}

	@Override
	public GroupCodeResultDto detailGroupCode(String groupCode) {
		GroupCodeResultDto resultDto = new GroupCodeResultDto();
		
		try {
			Optional<GroupCode> optionalGroupCode = groupCodeRepository.findById(groupCode);
			
			if( optionalGroupCode.isPresent() ) { // id 가 존재

				GroupCode detailGroupCode = optionalGroupCode.get(); // 영속화 O
				resultDto.setGroupCodeDto(GroupCodeDto.fromGroupCode(detailGroupCode));
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