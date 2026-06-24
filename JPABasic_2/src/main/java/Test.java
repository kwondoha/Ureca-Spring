
import java.util.Iterator;

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

//		// #1. 반복문을 통한 여러 건 등록
//		for (int i =1; i <= 3; i++) {
//			Product p = new Product();
//			p.setId(i);
//			p.setName("Phone" + i);
//			em.persist(p);
//		}

		// #2. Dirty Check - 최초 persist 후 변경 처리
		{
			Product p = new Product();
			p.setId(4);
			p.setName("Watch");
			
			em.persist(p); // 영속성 컨텍스트에 p 객체의 snapshot 등록
			
			p.setName("Glasses");
		}
		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 (flush() 호출)
		
		em.close();
		emf.close();
	}
}
