
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

		// #1. persist()로 초기 데이터 적재
//		{
//			Employee e = new Employee();
//			e.setId(1);
//			e.setName("홍길동");
//			e.setAddress("서울");
//			
//			em.persist(e);
//		}

		// #2. hibernate.shoq_sql 추가 후 persist()로 insert sql 확인
//		{
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("홍길동2");
//			e.setAddress("서울2");
//			
//			em.persist(e);
//		}

		// #3. hibernate.format_sql 추가 후 persist()로 초기 format된 insert sql 확인
//		{
//			Employee e = new Employee();
//			e.setId(3);
//			e.setName("홍길동3");
//			e.setAddress("서울3");
//			
//			em.persist(e);
//		}

		// 4. hibernate.hbm2ddl.auto 추가 후 create, update 차이 확인
		// create : 항상 시작 시 drop, create
		// update : 항상 시작 시 변경사항을 확인 후 , 있으면 alter 적용

		// #5. flush() vs commit()
//		{
//			Employee e = new Employee();
//			e.setId(10);
//			e.setName("BeforeFlush");
//			e.setAddress("BBB");
//
//			em.persist(e);
//
//			// flush() 1차 캐쉬에 관리되는 엔티티 객체의 snapshot부터 dirty check 변화 부분들을 DB에 반영한다
//			// flush()가 insert-update 자체에 변화를 주지는 않는다
//			// em.flush()가 있든없든 항상 insert-update 로 처리한다
//			em.flush(); // <---------- insert sql 수행, commit은 아직 안 됨
//
//			System.out.println("After Flush");
//
//			e.setName("AfterFlush");
//		}

		// #6. flush() vs commit() VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV2.
//		{
//			Employee e = new Employee();
//			e.setId(10);
//			e.setName("BeforeFlush");
//			e.setAddress("BBB");
//
//			em.persist(e);
//
//			em.flush();
//			
//			// 10초 대기 
//			Thread.sleep(10000);
//			
//			System.out.println("After Flush");
//
//			e.setName("AfterFlush");
//		}

		// #7. find()
//		{
//			Employee e = em.find(Employee.class, 10);
//			System.out.println(e);
//		}

		// #8. find() - Dirty Check
		// select - update(업데이트는 더티 체크가 여러번 발생해도 하나의 업데이트로 처리함 (필드가 달라도))
//		{
//			Employee e = em.find(Employee.class, 10);
//			System.out.println(e);
//			e.setAddress("제주");		// 더티1 
//			e.setAddress("제주2");	// 더티2 
//			e.setName("홍홍");		// 더티3
//			System.out.println(e);
//		}		

		// #9. merge()
		{
			
			// #9-1. 테이블에 없는 객체를 merge() - select & insert
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("이길동");
//			e.setAddress("강릉");
//			
//			em.merge(e);
			
			// #9-2. 테이블에 있는 객체를 merge() - select only
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("이길동");
//			e.setAddress("강릉");
//			
//			em.merge(e);

			// #9-2. 테이블에 있는 객체를 merge() & Dirty Check - select - update
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("이길동");
//			e.setAddress("강릉");
//
//			Employee e2 = em.merge(e);
//			e2.setAddress("부여");
		}
		
		// #10. remove() - finde(),select() & remove() delete
		{
			Employee e = em.find(Employee.class, em);
			em.remove(e);
			
		}
		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 (flush() 호출)

		em.close();
		emf.close();
	}
}