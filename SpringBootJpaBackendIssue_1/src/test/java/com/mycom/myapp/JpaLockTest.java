package com.mycom.myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.mycom.myapp.entity.DemoRow;
import com.mycom.myapp.entity.DemoVersionedRow;
import com.mycom.myapp.repository.DemoRowRepository;
import com.mycom.myapp.repository.DemoVersionedRowRepository;
import com.mycom.myapp.service.DemoLockService;

@SpringBootTest
public class JpaLockTest {

	@Autowired
	DemoLockService demoLockService; // 비관, 낙관적 Lock 을 통한 값 감소 서비스
	@Autowired
	DemoRowRepository demoRowRepository;
	@Autowired
	DemoVersionedRowRepository demoVersiondeRowRepository;	
	
	// 비관적 락 테스트 PK, 낙관적 락 테스트 PK
	Long pessimisticId;
	Long optimisticId;
	
	@BeforeEach
	void setUp() {
		pessimisticId = demoRowRepository.save(new DemoRow(50)).getId();
		optimisticId = demoVersiondeRowRepository.save(new DemoVersionedRow(50)).getId();
	}
	
	// 비관적 락은 처음부터 일렬로 줄서기 
	// 충돌 X
	@Test
	void pessismisticLock() throws Exception{
		int threads = 50;
		ExecutorService pool = Executors.newFixedThreadPool(threads);
		CountDownLatch latch = new CountDownLatch(threads);
		
		// 50 번 시도
		for (int i = 0; i < threads; i++) {
			pool.submit(() -> {
				try {
					demoLockService.decreaseWithPessimisticLock(pessimisticId);
				}finally {
					latch.countDown();
				}
			});
		}
		
		latch.await();
		pool.shutdown();
		// 50 번 시도한 모든 것이 성공하는 지 확인
		assertEquals(0, demoRowRepository.findById(pessimisticId).orElseThrow().getValue());
	}
	
	// 낙관적 락은 일렬로 줄세우기 X
	// 충돌 예상, 충돌 발생하면 재시도
	@Test
	void optimisticLock() throws Exception{
		int threads = 50;
		ExecutorService pool = Executors.newFixedThreadPool(threads);
		CountDownLatch latch = new CountDownLatch(threads);
		// 충돌 횟수 저장
		AtomicInteger conflicts = new AtomicInteger();
		
		// 50 번 시도
		for (int i = 0; i < threads; i++) {
			pool.submit(() -> {
				try {
					// 충돌이 생기면 성공할 때까지 반복작업
					while(true) {
						try {
							demoLockService.decreaseWithOptimisticLock(optimisticId);
							break; // while 벗어난다.
						}catch(ObjectOptimisticLockingFailureException e) { // 스프링프레임워크에서 시도 중 충돌 (version 값) 이 발생하면 이 예외를 준다.
							conflicts.incrementAndGet(); // 충돌 횟수 증가
						}
					}
					
				}finally {
					latch.countDown();
				}
			});
		}
		
		latch.await();
		pool.shutdown();
		// 50 번 시도한 모든 것이 성공하는 지 확인 X
		// 충돌이 발생하는 하는 경우, 재시도를 통해서 50 번 모두 성공하는 지 확인
		assertEquals(0, demoVersiondeRowRepository.findById(optimisticId).orElseThrow().getValue());		
	}
}
