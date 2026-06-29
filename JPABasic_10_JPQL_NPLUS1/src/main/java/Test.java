import java.util.List;

import entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// JPA N + 1 <= 성능 이슈, 1에 해당하는 sql 수행 + 불필요한 추가 N개의 sql 수행
// FetchType.LAZY 설정에 발생 + 기타 상황에서도 발생
// BusinessLogic에 맞게 적절한 fetch 전략이 필요한데 join fetch로 일반적으로 해결.
// JPA 구현체가 자동으로 sql을 작성, 실행하는 과정에서 발생할 수 있
public class Test {

	public static void main(String[] args) throws Exception {
		// persistence.xml 의 my-pu 로 EntityManager 를 EntityManagerFactory 로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();

		//// ------------------------ ManyToOne - EAGER
		// #1. find Comment
//		{
//			Comment comment = em.find(Comment.class,1);
//			System.out.println(comment);
//		}

		// #2. JPQL Comment 목록
		// N + 1 확인
//		{
//			String jpql = "select c from Comment c";

//			em.createQuery(jpql, Comment.class).getResultList();
//		}

		// #3. JPQL Comment 목록 + join post
		// N + 1 확인 <= join 이 추가되어도 select에 p가 없다
//		{
//			String jpql = "select c from Comment c join c.post";
//			
//			em.createQuery(jpql, Comment.class).getResultList();
//		}

		// #4. JPQL Comment 목록 + join post
		// N + 1 확인 <= join
//		{
//			String jpql = "select c,p from Comment c join c.post p";
//			
//			em.createQuery(jpql, Object[].class).getResultList();
//		}

		//// ------------------------ ManyToMany - LAZY

		// #5. JPQL Comment 목록 + join fetch
		// N + 1 확인 X
		// select[0m c1_0.id,c1_0.content,p1_0.id,p1_0.content,p1_0.title [34mfrom[0m
		// Comment c1_0 [34mjoin[0m Post p1_0 [34mon[0m p1_0.id=c1_0.post_id
//		{
//			String jpql = "select c from Comment c join fetch c.post";
//			
//			em.createQuery(jpql, Object[].class).getResultList();
//		}

		// #6. JPQL team 목록
		// [35m[Hibernate][0m [34mselect[0m t1_0.id,t1_0.name [34mfrom[0m teams
		// t1_0
		// N + 1 X
//		{
//			String jpql = "select t from Team t";
//			
//			em.createQuery(jpql, Team.class).getResultList();
//		}

		// #7. JPQL team 목록 + getUsers()
		// Team 목록 조회 후 각 팀의 getUsers()로 회원 수를 출력
		// N + 1 발생 <- 팀 수만큼 개별 select 수행
//		{
//			String jpql = "select t from Team t";
//
//			List<Team> list = em.createQuery(jpql, Team.class).getResultList();
//			list.forEach(team -> System.out.println(team.getUsers().size()));
//		}

		// #8. JPQL team 목록 + getUsers() <- #7.과 똑같은 비즈니스로 수행, 같은 결과를 얻으려고 함.
		// Team 목록 조회 후 각 팀의 getUsers()로 회원 수를 출력
		{
			String jpql = "select t from Team t join fetch t.users";

			List<Team> list = em.createQuery(jpql, Team.class).getResultList();
			list.forEach(team -> System.out.println(team.getUsers().size()));
		}
		
		em.close();
		emf.close();
	}
}
