package com.mycom.myapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.dto.CarDto;
import com.mycom.myapp.dto.EmpDto;

// 항상 데이터만 응답
@RestController // @Controller + @ResponseBody
public class JsonController {
	
	// 응답을 JSON
	@GetMapping("/dto")
	public CarDto getDto() {
		return new CarDto("소나타", 50000, "홍길동");
	}
	
	@GetMapping("/listdto")
	public List<CarDto> getListDto() {
		List<CarDto> list = new ArrayList<>();
		list.add(new CarDto("소나타", 50000, "홍길동"));
		list.add(new CarDto("아반떼", 40000, "이길동"));
		list.add(new CarDto("그랜져", 60000, "삼길동"));
		return list;
	}
	
	// 프론트 페이지, EmpDto, 프론트 -> JSON 요청
	// 프론트에서 Emp 객체 JAVAScript 객체를 json 으로 변환해서 백앤드에 post 전송 
	// 프론트에서 Emp 객체 List JAVAScript 객체를 json 으로 변환해서 백앤드에 post 전송 
	
	
	// postEmp(EmpDto dto) <- json이 아닌 일반 post parameter 
	@PostMapping("/emp")
	public Map<String, String> postEmp(@RequestBody EmpDto dto){
		System.out.println(dto);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	
	@PostMapping("/emplist")
	public Map<String, String> postEmpList(@RequestBody List<EmpDto> list){
		System.out.println(list);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
}
