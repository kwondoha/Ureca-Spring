import entity.Passport;
import entity.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Persistence;

public class Test {

	public static void main(String[] args) throws Exception{
		// persistence.xml 의 my-pu 로 EntityManager 를 EntityManagerFactory 로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin(); // transaction 준비, 영속성 컨텍스트(1차 캐시) 준비
		
		Person person = new Person();
		person.setName("홍길동");
		
		Passport passport = new Passport();
		passport.setNumber("KOR1234");
		
		
		// #1. 연결 없이 각각 persist
//		{
//			em.persist(person);
//			em.persist(passport);
//		}
		
		// #2. 연결 후 person 만 persist
		//     => org.hibernate.TransientPropertyValueException
//		{
//			person.setPassport(passport); // OneToOne 연결
//			em.persist(person);
//		}		
		
		// #3. 연결 후 모두 persist
		//     person -> passport
		//     insert 2 건 + Person update 1 건
//		{
//			person.setPassport(passport); // OneToOne 연결
//			em.persist(person);
//			em.persist(passport);
//		}			
		
		// #4. 연결 후 모두 persist
		//     passport -> person
		//     insert 2 건 <= Person 이 영속화될 때 passport 가 이미 영속화 되어있다.
//		{
//			person.setPassport(passport); // OneToOne 연결
//			em.persist(passport);
//			em.persist(person);
//		}
		
		// #5. @OneToOne(cascade=CascadeType.PERSIST) 설정 후
//		{
//			person.setPassport(passport); // OneToOne 연결
//			em.persist(person);
//		}	
		
		/// 양방향 테스트 
		
		// #6. Passport 에 @OneToOne(cascade=CascadeType.PERSIST) 설정 후
		//     OneToOne 양방향 + cascade 설정이 있더라도, Non-Owing 엔티티에 의한
		//     persist 는 Owing 엔티티의 연결이 null 처리된다.
//		{
//			passport.setPerson(person); // Passport OneToOne 연결
//			em.persist(passport);
//		}
		
		// #7. Passport 에 @OneToOne(cascade=CascadeType.PERSIST) 설정 후
		//     OneToOne 양방향 연결
		//     passport 와 별개로 person 에 passprt 연결하면 정상적 처리
		{
			person.setPassport(passport); // Person OneToOne 연결
			passport.setPerson(person); // Passport OneToOne 연결
			em.persist(passport);
		}
		
		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 ( 내부적으로 flush() 호출 )
		
		em.close();
		emf.close();
	}

}






















