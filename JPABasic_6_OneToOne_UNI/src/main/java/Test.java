import entity.Passport;
import entity.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Persistence;

public class Test {

	public static void main(String[] args) throws Exception {
		// persistence.xml의 my-pu 로 EntityManager 를 EntityManagerFactory로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin(); // transaction 준비, 영속성 컨테스트 준비
		// -------------------------------------------------------------------------------

		Person person = new Person();
		person.setName("hong");

		Passport passport = new Passport();
		passport.setNumber("KOR1234");

		// #1. 연결 없이 각각 persist
//		{
//			em.persist(person);
//			em.persist(passport);
//		}

		// #2. 연결 후 person만 persist

		// -> Caused by: java.lang.IllegalStateException:
		// org.hibernate.TransientPropertyValueException: object references an unsaved
		// transient instance - save the transient instance before flushing :
		// entity.Person.passport -> entity.Passport
//		{
//			person.setPassport(passport); // OneToOne 연결 ~!!~!
//			em.persist(person);
//		}

		// #3. 연결 후 persist
		// person -> passport
		// insert 2건 + Person update 1건
//		{
//			person.setPassport(passport); // OneToOne 연결 ~!!~!
//			em.persist(person);
//			em.persist(passport);
//		}

		// #4. 연결 후 persist
		// passport -> person
		// insert 2건 <- Person 이 영속화될 때 passport가 이미 영속화되어있다 ㄷㄷ 
//		{
//			person.setPassport(passport); // OneToOne 연결 ~!!~!
//			em.persist(passport);
//			em.persist(person);
//		}

		// #5. @OneToOne(cascade=CascadeType.PERSIST) 설정 완료
		{
			person.setPassport(passport); // OneToOne 연결 ~!!~!
			em.persist(person);
		}
		
		
		// -------------------------------------------------------------------------------
		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 (flush() 호출)

		em.close();
		emf.close();
	}
}