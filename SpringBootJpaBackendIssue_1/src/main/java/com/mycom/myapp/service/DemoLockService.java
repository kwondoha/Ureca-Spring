package com.mycom.myapp.service;

import org.springframework.stereotype.Service;

import com.mycom.myapp.entity.DemoRow;
import com.mycom.myapp.entity.DemoVersionedRow;
import com.mycom.myapp.repository.DemoRowRepository;
import com.mycom.myapp.repository.DemoVersionedRowRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemoLockService {
	private final DemoRowRepository demoRowRepository;
	private final DemoVersionedRowRepository demoVersionedRowRepository;
	
	// 비관적 락
	//for update 를 이용해서 DB에서 일렬로 줄을 선다
	@Transactional
	public void decreaseWithPessimisticLock(Long id) {
		DemoRow row = demoRowRepository.findByIdForUpdate(id).orElseThrow(); // 영속화
		row.setValue(row.getValue() - 1); // dirty check 대상
		demoRowRepository.saveAndFlush(row); // update sql 즉시 실행
	}
	
	// 낙관적 락
	// DB에서 일렬로 줄을 서지 않고 자유롭게 thread들이 호출함
	// 코드들이 빠르게 치되면 thread들이 충돌 예상이 어렵다 
	// -> 일부러 Thread.sleep()을 두어서 충돌을 유도함
	@Transactional
	public void decreaseWithOptimisticLock(Long id) {
		DemoVersionedRow row = demoVersionedRowRepository.findById(id).orElseThrow();
		row.setValue(row.getValue() - 1);
		try {
			Thread.sleep(30);
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt(); // 쓰레드 풀 예
		}
		demoVersionedRowRepository.saveAndFlush(row);
	}
	
	// @Modifying 조건부 update
	@Transactional
	public boolean  decreaseIfPositive(Long id) {
		// 반환 1 : 한 행 갱신 성공
		// 반환 0 : 한 행 갱신 실패
		return demoRowRepository.decreaseIfPositive(id) == 1;
	}
}
