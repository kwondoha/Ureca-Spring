package com.mycom.myapp.pattern.methodchain;

// 2개의 숫자를 더하거나 빼는 계산기 
public class Calculator {
	private int first;
	private int second;
	
	// #1. method chain pattern 적용 전
//	public void setFisrt(int first) {
//		this.first = first;
//	}
//
//	public void setSecond(int second) {
//		this.second = second;
//	}
//	
//	public void showAdd() {
//		System.out.println("Add " + this.first + " and " + this.second + " = " + (this.first + this.second));
//	}
//	
//	public void showSub() {
//		System.out.println("Sub " + this.first + " and " + this.second + " = " + (this.first - this.second));
//	}

	// #2. method chain pattern 적용 후 
	// return 자기자신
	public Calculator setFisrt(int first) {
		this.first = first;
		return this;
	}

	public Calculator setSecond(int second) {
		this.second = second;
		return this;
	}
	
	public Calculator showAdd() {
		System.out.println("Add " + this.first + " and " + this.second + " = " + (this.first + this.second));
		return this;
	}
	
	public Calculator showSub() {
		System.out.println("Sub " + this.first + " and " + this.second + " = " + (this.first - this.second));
		return this;
	}
	
	
}