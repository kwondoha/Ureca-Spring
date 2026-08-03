package com.mycom.myapp.entity;

import org.springframework.data.domain.Persistable;

import com.mycom.myapp.dto.CodeDto;
import com.mycom.myapp.dto.GroupCodeDto;
import com.mycom.myapp.entity.key.CodeKey;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Code implements Persistable<CodeKey>{

	@EmbeddedId
	private CodeKey codeKey; // PK
	
	@Column(name="code_name")
	private String codeName;
	
	@Column(name="code_name_brief")
	private String codeNameBrief;
	
	@Column(name="order_no")
	private int orderNo;

	// Persistable 인터페이스 관련
	@Override
	public CodeKey getId() {
		return this.codeKey;
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
	public static Code fromCodeDto(CodeDto codeDto) {
		return Code.builder()
				.codeKey(new CodeKey(codeDto.getGroupCode(), codeDto.getCode()))
				.codeName(codeDto.getCodeName())
				.codeNameBrief(codeDto.getCodeNameBrief())
				.orderNo(codeDto.getOrderNo())
				.build();
	}
}