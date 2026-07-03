package com.mycom.myapp.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.mycom.myapp.user.dto.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// User 와 UserRole 은 ManyToMany
// 만약 이 시스템이 User 와 UserRole 을 각각 관리 (예: crud) 한다면 ManyToMany 맞다.
// UserRole 은 백 오피스에서 관리 가정, 현재 프로젝트에서는 UserRole 일기만 => Login 기능만 고려
// User 와 UserRole 을 OneToMany 설정 <= User 당 할당된 UserRole 을 Authentication 과정에서 획득되어야 한다.
@Entity
@Setter
@Getter
@ToString
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	
	// 단방향
//	@OneToMany
	@OneToMany(fetch=FetchType.EAGER)  // 로그인 시점에 한꺼번에 userRoles 채운다.
//	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST) // user
	@ToString.Exclude  // Lombok 이 자동생성하는 toString() 에서 제외
	private List<UserRole> userRoles;
	
	// toUserDto()
	public UserDto toUserDto() {
		List<String> roles = new ArrayList<>();
		userRoles.forEach(userRole -> roles.add(userRole.getName()));
		return UserDto.builder().id(id).name(name).email(email).roles(roles).build();
	}
	
	public void fromUserDto(UserDto userDto) {
		this.id = userDto.getId();
		this.name = userDto.getName();
		this.email = userDto.getEmail();
		this.password = userDto.getPassword();
	}
}