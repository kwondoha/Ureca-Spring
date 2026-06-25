import entity.Student;
import entity.key.ProductKey;
import entity.key.StudentKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test2 {

	public static void main(String[] args) throws Exception {
		// persistence.xml의 my-pu 로 EntityManager 를 EntityManagerFactory로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin(); // transaction 준비, 영속성 컨테스트 준비

		// #1. @EmbeddedId, @Embeddable를 통한 복합키 처리 - Student 생성 
//		{
//			StudentKey key = new StudentKey();
//			key.setCode("uplus");
//			key.setNumber(1);
//			
//			Student s = new Student();
//			s.setId(key);;
//			s.setName("hong");
//			
//			em.persist(s);
//		}

		// #2. @EmbeddedId, @Embeddable를 통한 복합키 처리 - Student 조회
		{
			StudentKey key = new StudentKey();
			key.setCode("uplus");
			key.setNumber(1);
			
			Student s = em.find(Student.class, key);
			System.out.println(s);
		}

		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 (flush() 호출)

		em.close();
		emf.close();
	}
}