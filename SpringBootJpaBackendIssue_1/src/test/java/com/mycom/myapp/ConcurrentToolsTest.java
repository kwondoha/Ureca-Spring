package com.mycom.myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

// AtomicInteger
// ExecutorService : Thread 1000 개를 이용해서 처리? 1000 개의 Thread 를 만들던가, 일부만 만들고 Pool 로 1000 개 처럼 사용. ExecutorService 는 thread 를 pooling 방식
// CountDownLatch : main thread 가 대기하기 위해 join() 필요, 
//                  ExecutorService 를 이용하면 많은 Thread 를 사용하게 되고, 이 부분에 대한 join() 처리가 어렵다. 
//                  비동기 처럼 wait 하는 간편한 방법 등을 제공
public class ConcurrentToolsTest {

	@Test
	void executeLatchAtomic() throws Exception {
		int tasks = 100; // 제출할 작업 개수 ( 나중에 countDown 횟수와 같다. )
		int poolSize = 8; // 동시에 실행 가능한 thread 수
		
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);
		CountDownLatch latch = new CountDownLatch(tasks); // 남은 작업 수가 100 개
		AtomicInteger completed = new AtomicInteger(); // 작업 완료 수 : 0 기본값 초기화
		
		// main thread (test code 실행) 가 ExecutorService 에게 100 개 작업 할당
		// ExecutorService 의 pool 에 submit() 되는 순간부터 thread pool 에서 thread 들이 작업 수행, 동시에 main thread 도 경쟁하면서 for 문을 계속 수행
		for (int i = 0; i < tasks; i++) {
			pool.submit(() -> {
				try {
					completed.incrementAndGet();
				}finally {
					latch.countDown();
				}
			});
		}
		
		latch.await(); // Causes the current thread to wait until the latch has counted down to zero, unless the thread is interrupted.
		pool.shutdown();
		assertEquals(tasks, completed.get());
	}
}







