package com.mycom.myapp.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity 를 표현하는 Dto 는 비즈니스 로직에 맞게 필드를 추가, 변경, 삭제 가 가능
@Data
@Builder
// Controller에서 파라미터를 Dto화 하는 작업
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String name;
	private String email;
	private String password;
	
	private List<String> roles;
}
