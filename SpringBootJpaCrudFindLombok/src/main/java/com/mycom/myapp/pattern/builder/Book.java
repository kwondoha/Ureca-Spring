package com.mycom.myapp.pattern.builder;

//객체를 생성, 객체의 필드에 값을 채우는 과정의 비효율성을 개선하려고 함
//어떤 비효율성 ? 
//		필드가 많거나 복잡한 상황
//		#1. 생성자 + 파라미터 			-> 매우 비효율적
//		#2. 기본 생성자 + setter 		-> 혼선

// Simple 버전 Builder 패턴 적용
public class Book {
	private String isbn;
	private String title;
	private String author;
	private String  description;
	private int price;
	
	// 외부에서 생성자를 통한 객체 생성 불가 
	private Book() {}
	
	public static Book builder() {
		// 점검 또는 초기
		return new Book();
	}
	
	// setter 없음 ! 대신 field명과 동일한 메소드 체인이 적용된 메소드를 제공함
	public Book isbn(String isbn) {
		this.isbn = isbn;
		return this;
	}
	public Book title(String title) {
		this.title = title;
		return this;
	}
	public Book author(String author) {
		this.author = author;
		return this;
	}
	public Book description(String description) {
		this.description = description;
		return this;
	}
	public Book price(int price) {
		this.price = price;
		return this;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", description=" + description
				+ ", price=" + price + "]";
	}
}
