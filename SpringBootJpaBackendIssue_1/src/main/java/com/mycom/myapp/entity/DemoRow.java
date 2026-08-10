package com.mycom.myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 단순히 카운터 역할의 한 행
// find -> setValue -1 -> save
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DemoRow {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int value; // 감소되는 필드
	
	public DemoRow(int value) {
		this.value = value;
	}
}
