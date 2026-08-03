package com.mycom.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mycom.myapp.entity.Code;
import com.mycom.myapp.entity.key.CodeKey;

// 관리자용 X
// 공통코드가 필요한 비즈니스 도메인 클라이언트의 요청을 처리
// 복수개의 공통코드를 필요로 하는 상황
//    하나의 그룹코드가 아닌 , 복수 개의 그룹코드에 해당하는 코드 목록을 리턴
// private Map<String, List<CodeDto>> commonCodeListMap 객체로 return 하는 건 service layer 에서 처리, 현재는 해당하는 Code 목록 전체를 return
// 응답이 List<Code> 에는 복수개의 그룹코드에 해당하는 코드들이 함께 담겨진다.
public interface CommonCodeRepository extends JpaRepository<Code, CodeKey>{

	@Query("select c from Code c where c.codeKey.groupCode in :groupCodes order by c.codeKey.groupCode, c.orderNo")
	List<Code> findByGroupCodes(@Param("groupCodes") List<String> groupCodes);
}