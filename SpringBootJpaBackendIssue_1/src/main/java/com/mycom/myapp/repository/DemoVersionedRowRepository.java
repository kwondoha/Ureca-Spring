package com.mycom.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.entity.DemoVersionedRow;

// 낙관적 Lock
// 기본 CRUD 가능, 낙관적 Lock은 @Lock 사용하지 않음
public interface DemoVersionedRowRepository extends JpaRepository<DemoVersionedRow, Long>{

}
