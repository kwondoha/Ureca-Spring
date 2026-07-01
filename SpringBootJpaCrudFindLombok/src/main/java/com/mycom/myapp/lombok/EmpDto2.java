package com.mycom.myapp.lombok;

import lombok.Data;

// @AllArgsConstructor // <= final 또는 일반 필드 모두 받는 생성자
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//public class EmpDto2 {
//	private int employeeId;
//	private String firstName;
//	private String lastName;
//	private String email;
//	private String hireDate;
//}

//@RequiredArgsConstructor // <= final 등 반드시 초기화 되어야 하는 필드만 생성자 파라미터에 추가
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//public class EmpDto2 {
//	
//	// 객체가 생성될 때 반드시 초기화되어야 하는 필드
//	// 기본 생성자로 초기화 X
//	private final String departmentId;
//	private final int salary;
//	
//	private int employeeId; // @RequiredArgsConstructor 에 의한 생성자 파라미터에 없다.
//}

// 기본 생성자, 전체 파라미터 생성자 대신 빌더 패턴을 이용하는 것이 더 일반화
// 반드시 초기화 되어야 하는 필드 (불변) 을 생성자로 만들어야 하는 필요
@Data // <= 위 5개 annotation 을 합친 효과
public class EmpDto2 {
	
	// 객체가 생성될 때 반드시 초기화되어야 하는 필드
	// 기본 생성자로 초기화 X
	private final String departmentId;
	private final int salary;
	
	private int employeeId; // @RequiredArgsConstructor 에 의한 생성자 파라미터에 없다.
}
