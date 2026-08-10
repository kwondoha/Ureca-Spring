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
public class JpaModifyingTest {

	@Autowired
	DemoLockService demoLockService; // 비관, 낙관적 Lock 을 통한 값 감소 서비스
	@Autowired
	DemoRowRepository demoRowRepository;
	
	// 한도 대상 id
	Long id;
	
	@BeforeEach
	void setUp() {
		id = demoRowRepository.save(new DemoRow(10)).getId(); // 10 의 한도
	}
	
	// 0 에서 멈추는 작업 테스트
	@Test
	void conditionalUpdateStopsAtZero() throws Exception{
		int threads = 40; // 10 개 성공, 30 개 실패
		ExecutorService pool = Executors.newFixedThreadPool(16);
		CountDownLatch latch = new CountDownLatch(threads);
		
		AtomicInteger success = new AtomicInteger();
		AtomicInteger fail = new AtomicInteger();
		
		// 40 번 시도
		for (int i = 0; i < threads; i++) {
			pool.submit(() -> {
				try {
					if( demoLockService.decreaseIfPositive(id) ) {
						success.incrementAndGet();
					}else {
						fail.incrementAndGet();
					}
				}finally {
					latch.countDown();
				}
			});
		}
		
		latch.await();
		pool.shutdown();
		// 40 번 시도 중 성공 10 회, 실패 30 회 
		// 결과적으로 성공 10 회에 의해 최초 10 -> 0 감소, 음수 X
		assertEquals(0, demoRowRepository.findById(id).orElseThrow().getValue());
		assertEquals(10, success.get());
		assertEquals(30, fail.get());
	}
}







