package com.mycom.myapp.lombok;

import lombok.Builder;
import lombok.ToString;

// lombok 사용하지 않는 Dto
@Builder
@ToString
public class EmpDto {
	private int employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private String hireDate;
	
//	public EmpDto() {}
//	public EmpDto(int employeeId, String firstName, String lastName, String email, String hireDate) {
//		super();
//		this.employeeId = employeeId;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.hireDate = hireDate;
//	}
//	
//	public int getEmployeeId() {
//		return employeeId;
//	}
//	public void setEmployeeId(int employeeId) {
//		this.employeeId = employeeId;
//	}
//	public String getFirstName() {
//		return firstName;
//	}
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//	public String getLastName() {
//		return lastName;
//	}
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getHireDate() {
//		return hireDate;
//	}
//	public void setHireDate(String hireDate) {
//		this.hireDate = hireDate;
//	}
//	
//	@Override
//	public String toString() {
//		return "EmpDto [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
//				+ email + ", hireDate=" + hireDate + "]";
//	}
//	
//	@Override
//	public int hashCode() {
//		return Objects.hash(email, employeeId, firstName, hireDate, lastName);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		EmpDto other = (EmpDto) obj;
//		return Objects.equals(email, other.email) && employeeId == other.employeeId
//				&& Objects.equals(firstName, other.firstName) && Objects.equals(hireDate, other.hireDate)
//				&& Objects.equals(lastName, other.lastName);
//	}
	
}
