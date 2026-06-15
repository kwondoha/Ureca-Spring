package com.mycom.myapp.dto;

public class CarDto {
	private String name;
	private int price;
	private String owner;
	
	// 기본 생성자가 없고, int price의 파라미터가 전달되지 않으면 int <- null 오류 발생 
	// 기본 생성자가 항상 있어야 함 ~~!
	public CarDto() {}
	public CarDto(String name, int price, String owner) {
		super();
		this.name = name;
		this.price = price;
		this.owner = owner;
	}
	
	// ~Name -> ~Name2
	public String getName2() {
		return name;
	}
	public void setName2(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	@Override
	public String toString() {
		return "CarDto [name=" + name + ", price=" + price + ", owner=" + owner + "]";
	}
	
}
