import entity.Passport;
import entity.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Persistence;

public class Test2 {

	public static void main(String[] args) throws Exception{
		// persistence.xml 의 my-pu 로 EntityManager 를 EntityManagerFactory 로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin(); // transaction 준비, 영속성 컨텍스트(1차 캐시) 준비
		
		// fetch
		
		// #1. Person find
		//     join, OneToOne 관계에서 Fetch 기본 EAGER (즉시)
//		{
//			Person person = em.find(Person.class, 1);
//			System.out.println(person);
//		}
		
		// #2. Passport find
		//     join, OneToOne 관계에서 Fetch 기본 EAGER (즉시) <= Non-Owing 엔티티에도 동일
//		{
//			Passport passport = em.find(Passport.class, 1);
//			System.out.println(passport);
//		}		
		
		// #3. Person find  <= FetchType.LAZY
		//     person 만 select. 단, toString 에 passport 포함되면 별도의 passport select 가 추가로 실행. 
//		{
//			Person person = em.find(Person.class, 1);
//			System.out.println(person);
//		}	
		
		// #4. Person find  <= FetchType.LAZY
		//     person find 후 person 엔티티 객체와 연결된 passport 가 필요한 상황
		//     LAZY 의 person find 는 person 만 select
		//     person 과 연결된 passport 를 select 하면 join 실행 ( person 까지 )
		//     passport 가 @OneToOne 이고 default 가 EAGER 이기 때문
//		{
//			Person person = em.find(Person.class, 1);
//			System.out.println(person);
//			Passport passport = person.getPassport();
//			System.out.println(passport);
//		}	
		
		// #5. Person, Passport 모두 FetchType.LAZY
		//     person find 후 person 엔티티 객체와 연결된 passport 가 필요한 상황
		//     LAZY 의 person find 는 person 만 select
		//     person 과 연결된 passport 를 select 하면 join 실행 ( person 까지 )
		//     passport 가 @OneToOne 이고 default 가 EAGER 이기 때문
		{
			Person person = em.find(Person.class, 1);
			System.out.println(person);
			Passport passport = person.getPassport();
			System.out.println(passport);
		}	
		
/*
Hibernate: select p1_0.id,p1_0.name,p1_0.passport from Person p1_0 where p1_0.id=? 
   <= Person person = em.find(Person.class, 1);
Person [id=1, name=홍길동]
   <= System.out.println(person);
Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
   <= Passport passport = person.getPassport();
Hibernate: select p1_0.id,p1_0.name,p1_0.passport from Person p1_0 where p1_0.passport=? ???
   <= 영속화 된 Passport 가 OneToOne 관계의 Person 찾기 위해 수행
Passport [id=1, number=KOR1234]		
 */
		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 ( 내부적으로 flush() 호출 )
		
		em.close();
		emf.close();
	}

}






















