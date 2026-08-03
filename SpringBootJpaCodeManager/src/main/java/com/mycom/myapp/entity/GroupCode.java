package com.mycom.myapp.entity;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;

import com.mycom.myapp.dto.GroupCodeDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="group_code")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupCode implements Persistable<String>{
	
	// 주의 Auto Increment Key X
	// insert 할 때 key 제공 <= Spring Data Jpa 기본 처리 방식이 save() id 가 있으면 merge() 사용
	// 비효율적임. Spring Data Jpa 에게 id 정책을 우리가 직접 표현 <= Persistable 구현 <= isNew() 의존
	@Id
	@Column(name="group_code")
	private String groupCode;
	
	@Column(name="group_code_name")
	private String groupCodeName;
	
	@Column(name="group_code_desc")
	private String groupCodeDesc;

	// Persistable 인터페이스 관련
	@Override
	public @Nullable String getId() {
		return this.groupCode;
	}

	// @Getter 에 의해 isNew() 만들어지지만 명시적으로 표현
	@Override
	public boolean isNew() {
		return this.isNew;
	}
	
	// 영속화 제외
	@Transient
	private boolean isNew;
	
	// Dto -> Entity
	public static GroupCode fromGroupCodeDto(GroupCodeDto groupCodeDto) {
		return GroupCode.builder()
				.groupCode(groupCodeDto.getGroupCode())
				.groupCodeName(groupCodeDto.getGroupCodeName())
				.groupCodeDesc(groupCodeDto.getGroupCodeDesc())
				.build();
	}	
}