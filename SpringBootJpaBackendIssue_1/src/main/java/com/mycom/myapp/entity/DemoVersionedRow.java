package com.mycom.myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 단순히 카운터 역할의 한 행
// find -> setValue -1 -> save
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DemoVersionedRow {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int value; // 감소되는 필드
	
	// JPA 구현체가 낙관적 락을 자동으로 On함
	// find 등으로 가져올 때 version 책정
	// flush/save 등 update 할 때, where 이전의 find할 때 책정되어있는 version을 where 조건으로 추가
	@Version
	private Long version;
	
	public DemoVersionedRow(int value) {
		this.value = value;
	}
}
