package com.mycom.myapp.pattern.builder;


public class Test {

	public static void main(String[] args) {
		// #1. Book
//		Book book = Book.builder().isbn("1234").title("한국 축구").author("김대섭")
//				.description("2030년 월드컵을 위한").price(5000);
//		System.out.println(book);
		
		// #2. Board
//		Board board = new Board.Builder().title("제목1").content("글2")
//				.category("A게시판").build();
//		System.out.println(board);

		// #3. title 누락 
		Board board = new Board.Builder().content("글2")
				.category("A게시판").build();
		System.out.println(board);
	}

}
