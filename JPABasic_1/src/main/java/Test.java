
import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Spring 또는 별도의 서버 없이 local main()에서 JPA Hibernate 실행환경구축 
public class Test {

	public static void main(String[] args) {
		// persistence.xml의 my-pu 로 EntityManager 를 EntityManagerFactory로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin(); // transaction 준비, 영속성 컨테스트 준비

		// #1. 1건 persist
//		{
//			Product p = new Product();
//			p.setId(1);
//			p.setName("Book"); // p 는 현재 일반 자바 객체 (New)
//
//			em.persist(p); // p 가 영속성 컨텍스트에 등록됨 : DB insert는 아님
//		}

		// #2. 여러 건 persist
		{
			Product p2 = new Product();
			p2.setId(2);
			p2.setName("Phone");

			Product p3 = new Product();
			p3.setId(3);
			p3.setName("Car");

			em.persist(p2);
			em.persist(p3);
		}

		// persist() : New 객체를 영속성 컨텍스트에 추가 (snapshot 등록 - 관리), SQL과 무관함,
		// flush() 	 : 영속성 컨텍스트에 관리되는 객체 ->DB 반영, 새로운 객체 - insert, 기존 객체 - update, SQL 수행
		// commit()  : DB 반영 확정 

		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 (flush() 호출)
		em.close();
		emf.close();
	}
}
