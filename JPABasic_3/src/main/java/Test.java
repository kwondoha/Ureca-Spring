
import java.util.Iterator;

import entity.Employee;
import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {

	public static void main(String[] args) {
		// persistence.xml의 my-pu 로 EntityManager 를 EntityManagerFactory로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin(); // transaction 준비, 영속성 컨테스트 준비

		// #1. Product 1건 persist
//		{
//			Product p = new Product();
//			p.setId(1);
//			p.setName("Book");
//			
//			em.persist(p);
//		}
		
		
		// #2. DB -> Entity 객체, 영속화 <- find()
//		{
//			Product p = em.find(Product.class, 1);
//			System.out.println(p);
//			p.setName("Phone");
//			System.out.println(p);
//		}
		
		// #3. persist - PK 중복
//		{
//			Employee e = new Employee();
//			e.setId(1);
//			e.setName("일길동");
//			e.setAddress("대전");
//			
//			em.persist(e);
//		}
		
		// #4. find() + Dirty Check 
//		{
//			Employee e = em.find(Employee.class, 1);
//			System.out.println(e);
//			e.setAddress("대");
//			System.out.println(e);		
//		}
		
		// #5. merge( ) : DB에 없으면 insert
		{
//			Employee e = new Employee();
//			e.setId(3);
//			e.setName("삼길동");
//			e.setAddress("광주");
//			
			// #5-1.
//			em.merge(e); // 영속화 되었기때문에 insert 수행
//			
//			// 위의 insert 테스트 후에 3번 데이터 삭제
//			
//			e.setAddress("abc"); // dirty check되지 않음
//			// b = merge(a)에서 영속화되는 객체는 a가 아니라 b
//			// a 객체는 merge()에게 id 등 단순 정보 제공
//		
//			// #5-2. 
//			Employee e2 = em.merge(e);
//			System.out.println(e);
//			System.out.println(e2);
//			System.out.println(e == e2);
//			
//			// e2에 대한 Dirty-Check
//			e.setAddress("abc");
//			e2.setAddress("def");
//			// def로 바뀌어있음.
		}
		
		// #6. merge( ) : DB에 있으면 update 
//		{
//			Employee e = new Employee();
//			e.setId(3);
//			e.setName("삼길동");
//			e.setAddress("광주");
//			
//			// #5-1.
//			em.merge(e); 
//		}
		
		// #7. detach() - 엔티티 객체를 영속성 컨텍스트에서 제외, 분리
//		{
//			Employee e = em.find(Employee.class, 2);
//			System.out.println(e);
//			e.setAddress("abc");
//			em.detach(e);
//			e.setAddress("def");
//			System.out.println(e);
//		}
		
		// #8. remove() - delete는 영속화된 엔티티 객체만 해당됨 
//		{
//			Employee e = em.find(Employee.class, 2);
//			System.out.println(e);
//			em.remove(e);
//		}
		
		// #8. remove() - 비영속객체 Delete
//		{
//			Employee e = new Employee();
//			e.setId(3);
//			e.setName("일길동asd");
//			e.setAddress("대전aaaaaa");			
//			
//			System.out.println(e);
//			em.remove(e);
//		}
		
		
		// #9. @Table, @Column을 이용해서 DB 테이블명 및 컬럼명, 속성 등을 변경\
		{
			Employee e = new Employee();
			e.setId(1);
			e.setName("일길동a");
			e.setAddress("대전a");			
			
			em.persist(e);
		}
		
		
		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 (flush() 호출)
		
		em.close();
		emf.close();
	}
}
