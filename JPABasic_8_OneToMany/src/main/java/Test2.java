import java.util.List;

import entity.Comment;
import entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Persistence;

public class Test2 {

	public static void main(String[] args) throws Exception {
		// persistence.xml 의 my-pu 로 EntityManager 를 EntityManagerFactory 로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin(); // transaction 준비, 영속성 컨텍스트(1차 캐시) 준비
		//-------------------------------------------------------------------------------------

		// #1. Post만 find 
		// -> post만 select : FetchType.LAZY
//		{
//			Post p = em.find(Post.class, 1);
//			System.out.println(p);
//		}
		
		// #2. Comment만 find
		// -> comment만 select 
//		{
//			Comment c1 = em.find(Comment.class, 1);
//			System.out.println(c1);
//		}
		
		// #3. Post find - getComments()
		// -> post, commment 둘다 select
//		{
//			Post p = em.find(Post.class, 1);
//			List<Comment> comments = p.getComments(); // select X : 지연 컬렉션 프록시
//			
//			Thread.sleep(5000); // 의도적 지연
//			
//			for (Comment c : comments) { // Post_comment, Comment join. where post_id 
//				System.out.println(c);
//			}
//		}
		
		// #4. Fetchtype.EAGER, Post find - getComments()
		{
			Post p = em.find(Post.class, 1);
			List<Comment> comments = p.getComments(); // Post, Post_comment, Comment 3개 join. where post_id 
			
			Thread.sleep(5000); // 의도적 지연
			
			for (Comment c : comments) { // 별도의 select 수행 X 
				System.out.println(c);
			}
		}
		
		// #5. Fetchtype.EAGER, Post find - getComments() + add Comment
		// -> c3 insert 후 , Post_Comment 중 post_id 해당하는 데이터 전체 삭제 후,
		// post와 연결된 comment 3개 insert 수행 
		{
			Post p = em.find(Post.class, 1);
			List<Comment> comments = p.getComments(); // Post, Post_comment, Comment 3개 join. where post_id 
			
			Comment c3 = new Comment();
			comments.add(c3);
			
			em.persist(c3);
		}
		
		//-------------------------------------------------------------------------------------
		em.getTransaction().commit(); // transaction 완료, 확정 -> DB 반영 ( 내부적으로 flush() 호출 )

		em.close();
		emf.close();
	}

}
