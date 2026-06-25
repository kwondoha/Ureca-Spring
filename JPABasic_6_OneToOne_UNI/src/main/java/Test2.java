import entity.Passport;
import entity.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Persistence;

public class Test2 {

	public static void main(String[] args) throws Exception {
		// persistence.xml의 my-pu 로 EntityManager 를 EntityManagerFactory로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin(); // transaction 준비, 영속성 컨테스트 준비
		// -------------------------------------------------------------------------------


		// fetch 
		
		// #1. Person find
		// OneToOne 관계에서 Fetch 기본 EAGER(즉시) 
//		{
//			Person person = em.find(Person.class, 1);
//			System.out.println(person);
//		}
		
		// #2. Passport find
		// join 없이 Passport 만 select 
//		{
//			Passport passport = em.find(Passport.class, 1);
//			System.out.println(passport);
//		}
		
		// #3. Person find <---------------- FetchType.LAZY 
		// person만 select. 단, toString에 passport 포함되면 별도의 passport select가 추가로 실행
//		{
//			Person person = em.find(Person.class, 1);
//			System.out.println(person);
//		}
		
		// #4. Person find <- FetchType.LAZY
		// person find 후 person 엔티티 객체와 연결된 passport가 필요한 상황
		{
			Person person = em.find(Person.class, 1);
			System.out.println(person);
			Passport passport = person.getPassport();
			System.out.println(passport);
		}
		// -------------------------------------------------------------------------------
		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 (flush() 호출)

		em.close();
		emf.close();
	}
}