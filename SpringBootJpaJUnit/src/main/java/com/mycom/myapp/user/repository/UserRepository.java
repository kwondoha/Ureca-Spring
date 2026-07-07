package com.mycom.myapp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mycom.myapp.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	// 회원 가입은 User 엔티티의 insert <= JpaRepository 에서 이미 지원
	// 회원 가입 시 이메일 중복 검사 - existsByEmail
	boolean existsByEmail(String email);
	
	
	// ------------------------------------- 이전 프로젝트에서 완성
	// 로그인은 email, password 로 검증
	// repository 는 email 로 User find
	// email, password 일치 검증은 Service 에서 처리
//	Optional<User> findByEmail(String email);
	
	// 더 많은 User 관련 메소드 선언이 있을 것. 
	
//	Optional<User> findById(Long id);
	
	// JPQL + join fetch + EAGER => select 1건
	// 난 지금 User 만 사용할 건데, User 에 OneToMany 인 UserRole 도 함께 영속화
	@Query("select u from User u join fetch u.userRoles where u.email= :email")
	Optional<User> findByEmail(@Param("email") String email);
	
	// JPQL + EAGER => select 2건
	// JPQL 은 email 로 User 엔티티만 영속화, User 에 OneToMany 인 UserRole 도 추가로 영속화
//	@Query("select u from User u where u.email= :email")
//	Optional<User> findByEmail(@Param("email") String email);	
	
	// JPQL + join + EAGER => select 2건
	// JPQL 이 UserRoles 와 join 을 수행하지만, UserRoles 를 사용하지 않기 때문에 
	//    UserRoles 는 단순 JPQL 수행 용도. email 로 User 엔티티만 영속화, User 에 OneToMany 인 UserRole 도 추가로 영속화
//	@Query("select u from User u join u.userRoles where u.email= :email")
//	Optional<User> findByEmail(@Param("email") String email);	
	
	// JPQL + join + LAZY => 예외 발생 <= open-in-view 이 false 일 때, true 면 오류 X
	// open-in-view 이 false 이므로 JPA 영속성 컨텍스트는 repository 의 단계에서 종료
	// 연관관계가 LAZY 설정된 엔티티를 영속화 후 연관관계의 엔티티를 사용할 때 영속화 종료로 예외 발생
	
//	@Query("select u from User u join u.userRoles where u.email= :email")
//	Optional<User> findByEmail(@Param("email") String email);		
}



