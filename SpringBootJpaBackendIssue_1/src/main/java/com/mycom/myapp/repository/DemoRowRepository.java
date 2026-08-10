package com.mycom.myapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mycom.myapp.entity.DemoRow;

import jakarta.persistence.LockModeType;

public interface DemoRowRepository extends JpaRepository<DemoRow, Long>{
	
	// 비관적 락 조회
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select d from DemoRow d where d.id = :id")
	Optional<DemoRow> findByIdForUpdate(@Param("id") Long id);
	
	// 한도 내 처리
	@Modifying(clearAutomatically=true, flushAutomatically=true)
	@Query("update DemoRow set d.value = d.value - 1 where d.id = :id and d.value > 0")
	int decreaseIfPositive(@Param("id") Long id);
}
