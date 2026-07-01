package com.mycom.myapp.pattern.builder;

// 상용 버전 Builder 패턴 적용 
// 객체와 동일한 필드를 가진 Inner Class
public class Board {

//	private String title;
//	private String content;
//	private String category;
	
	// 추가로 불변 객체 - 필드값이 초기화된 후 변하지 않는 객체 -> field final 적용 
	private final String title;
	private final String content;
	private final String category;
	
	// 생성자
	Board(Builder builder){
		this.title = builder.title;
		this.content = builder.content;
		this.category = builder.category;
	}
	
	// 메소드 체인 패턴으로 필드와 동일한 메소드를 가진다
	// 부모 클래스의 객체를 생성해서 리턴하는 build()를 가진다
	public static class Builder{
		private String title;
		private String content;
		private String category;
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder content(String content) {
			this.content = content;
			return this;
		}
		
		public Builder category(String category) {
			this.category = category;
			return this;
		}
		
		public Board build() {
			// 검증
			// 제목 검증
			if(title == null || title.isBlank()) {
				throw new IllegalArgumentException("제목 누락");
			}
			return new Board(this);
		}
	}
	
	@Override
	public String toString() {
		return "Builder [title=" + title + ", content=" + content + ", category=" + category + "]";
	}
	
}
