package com.mycom.myapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentValidationDto {
	// 사전에 미리 제공하는 규칙
	// @Pattern, @Size....
	@NotBlank(message="이름은 필수입니다.")
	@Size(min=2, max=20, message="이름은 2글자 이상 20글자 이하이어야 합니다.")
	private String name;
	
	@NotBlank(message="이메일은 필수입니다.")
	@Email(message="올바른 이메일 형식이 아닙니다.")
	private String email;
	
	@NotBlank(message="전화번호는 필수입니다.")
	@Pattern(regexp="^01[0-9]-\\d{3,4}-\\d{4}$", message="전화번호 형식은 01X-XXXX-XXXX 입니다.")
	private String phone;
}
