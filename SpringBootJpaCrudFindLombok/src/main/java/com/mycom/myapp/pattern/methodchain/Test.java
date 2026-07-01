package com.mycom.myapp.pattern.methodchain;

// 동일 객체의 반복적인 객체 참조 코드 개선
public class Test {

	public static void main(String[] args) {

		Calculator calculator = new Calculator();
		
		// #1. method chain pattern 적용 전
//		calculator.setFisrt(3);
//		calculator.setSecond(5);
//		calculator.showAdd();
//		calculator.setFisrt(7);
//		calculator.showSub();
		
		
		// #2. method chain pattern 적용 후 
		calculator.setFisrt(3).setSecond(5).showAdd().setFisrt(7).showSub();
	}
}
