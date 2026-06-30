package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Controller 단위로 CORS 설정 가능
// 효율적인 관리를 위해서 별도의 Java Configuration 처리가 일반적임
@RestController
public class CORSController {

	@GetMapping("/cors")
	public Map<String, String> getCORS(@RequestParam("param") String param){
		System.out.println("getCORS param : " + param);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	
	@PostMapping("/cors")
	public Map<String, String> postCORS(@RequestParam("param") String param){
		System.out.println("postCORS param : " + param);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	
	@PutMapping("/cors/{num}")
	public Map<String, String> putCORS(@RequestParam("param") String param, @PathVariable("num") String num){
		System.out.println("putCORS param : " + param);
		System.out.println("putCORS num : " + num);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map; 
	}
	
	@DeleteMapping("/cors/{num}")
	public Map<String, String> deleteCORS(@PathVariable("num") String num){
		System.out.println("deleteCORS num : " + num);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
}
