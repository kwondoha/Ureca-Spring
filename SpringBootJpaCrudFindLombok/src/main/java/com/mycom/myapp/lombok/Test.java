package com.mycom.myapp.lombok;

public class Test {

	public static void main(String[] args) {
		// lombok EmpDto2 객체로 테스트
		EmpDto2 dto = new EmpDto2("1234", 5000);
		dto.setEmployeeId(12); // setter
		System.out.println(dto.getSalary()); // getter
		System.out.println(dto);

		// equals & hashcode
		EmpDto2 dto2 = new EmpDto2("1234", 5000);
		dto2.setEmployeeId(12); // setter
		System.out.println(dto2);

		System.out.println(dto.equals(dto2));

		// builder
		EmpDto empDto = EmpDto.builder().employeeId(111).firstName("길동")
				.lastName("홍").email("ee@ee.com").build();
		System.out.println(empDto);
	}

}
