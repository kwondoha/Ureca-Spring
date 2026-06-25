import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {

	public static void main(String[] args) throws Exception {
		// persistence.xml의 my-pu 로 EntityManager 를 EntityManagerFactory로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin(); // transaction 준비, 영속성 컨테스트 준비

		// #1. @GeneratedValue(strategy=GenerationType.IDENTITY)
//		{
//			Employee emp = new Employee();
////			emp.setId(0); 생략
//			emp.setName("hong");
//			emp.setAddress("seoul");
//
//			em.persist(emp);
//
//			Employee emp2 = new Employee();
//			emp2.setName("lee");
//			emp2.setAddress("yeoju");
//
//			em.persist(emp2);
//		}

		// #2. @GeneratedValue(strategy=GenerationType.AUTO)
		{
			Employee emp = new Employee();
			emp.setId(1); // 0 은 오류없이 처리된다. 0 은 객체 초기화와 동일한 상태이므로 jpa 에서 id 로 인식 X, 초기화된 상태로만 인식			emp.setName("hong");
			emp.setAddress("seoul");

			em.persist(emp);
		}

		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 (flush() 호출)

		em.close();
		emf.close();
	}
}