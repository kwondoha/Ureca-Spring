import java.util.List;

import entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {

	public static void main(String[] args) throws Exception{
		// persistence.xml 의 my-pu 로 EntityManager 를 EntityManagerFactory 로부터 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		
		// #1. Orders.findByCustomerName
//		{		
//			em.createNamedQuery("Orders.findByCustomerName", Orders.class)
//				.setParameter("customerName", "고객1")
//				.getResultList().forEach( a -> System.out.println( a ));
//		}

		// #2. Orders.findByOrderDate
//		{		
//			em.createNamedQuery("Orders.findByOrderDate", Orders.class)
//				.setParameter("orderDate", LocalDate.of(2025, 4, 16))
//				.getResultList().forEach( a -> System.out.println( a ));
//		}		
		
		// #3. Orders.findByOrderRange
//		{		
//			em.createNamedQuery("Orders.findByOrderRange", Orders.class)
//				.setParameter("startDate", LocalDate.of(2025, 4, 13))
//				.setParameter("endDate", LocalDate.of(2025, 4, 14))
//				.getResultList().forEach( a -> System.out.println( a ));
//		}
			
		// #4. Orders.findByProductPriceRange
//		{		
//			em.createNamedQuery("Orders.findByProductPriceRange", Object[].class)
//				.setParameter("startPrice", 3000)
//				.setParameter("endPrice", 4000)
//				.getResultList().forEach( a -> System.out.println( a[0] + ", " + a[1] ));
//		}
		
		// #5. Native Query
		{
			String sql = """
					select o.* 
					  from orders o, customer c
					 where o.customer_id = c.id
					   and c.name = :customerName
					""";
			List<?> list = em.createNativeQuery(sql, Orders.class)
				.setParameter("customerName", "고객1")
				.getResultList();
			
			list.forEach( a -> System.out.println(a));
		}
		em.close();
		emf.close();
	}

}






















