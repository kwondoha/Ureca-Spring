package com.mycom.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mycom.myapp.entity.Code;
import com.mycom.myapp.entity.key.CodeKey;

// 기본적인 그룹코드의 CRUD 수행
// groupCode 에 맞는 코드 목록 리턴하는 메소드가 필요
public interface CodeRepository extends JpaRepository<Code, CodeKey>{

	@Query("select c from Code c where c.codeKey.groupCode = :groupCode order by c.orderNo")
	List<Code> findByGroupCode(@Param("groupCode") String groupCode);
}