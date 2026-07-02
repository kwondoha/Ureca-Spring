package com.mycom.myapp.user.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

//Entity 를 표현하는 Dto 는 비즈니스 로직에 맞게 필드를 추가, 변경, 삭제 가 가능
@Data
@Builder
public class UserDto {
	private long id;
	private String name;
	private String email;
	private String password;
	
	private List<String> roles;
}
