package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // JPA 가 관리하는 클래스로 DB 의 테이블과 매핑
public class Product {
	
	@Id 
	private int id; // JPA가 관리하는 클래스는 반드시 PK 의 필드를 가져야 하고, @Id로 표시함
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}
	
	
}
