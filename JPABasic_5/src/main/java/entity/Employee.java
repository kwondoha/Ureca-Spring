package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY) 	// 권장. Auto Increment
//	@GeneratedValue(strategy=GenerationType.AUTO)		// Hibernate가 DB에 맞게 알아서 선택 (MySQL -> SEQUENCE 유) 
//	@GeneratedValue(strategy=GenerationType.SEQUENCE)	// DB의 SEQUENCE 이용. 지원하지 않는 DB의 경우에는 Hibernate가 DB에 맞게 알아서 선택 (
//	@GeneratedValue(strategy=GenerationType.TABLE)		// HibernateDPRP TABLE 생성해서 id를 관리해달라고 요청
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	// UUID 버전 ~~~~~~~~~~~~~~	
//	@Id
//	@GeneratedValue(strategy=GenerationType.UUID)
//	private String id;
//	
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}

	private String name;
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
}
