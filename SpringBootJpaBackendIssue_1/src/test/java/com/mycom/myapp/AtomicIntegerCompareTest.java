package com.mycom.myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

// 일반 int 증감 vs AtomicInteger 증감
public class AtomicIntegerCompareTest {

	private static final int TIMES = 50000;
	
	@Test
	void plainInt() throws Exception{
		// 단순히 int 선언하면 thread 를 복잡하게 생성해야 한다. 우리는 단순히 lambda 로 쉽게 thread 를 생성하려고 함.
		// 하지만, lambda 는 내부적으로 참조값이 아닌 변수를 사용 X
//		int plain = 0; 
		final int[] plain = {0}; // lambda 에서 참조할 수 있도록 final 배열 객체를 만들고 그 안의 값을 plain int 
		
		Thread a = new Thread( () -> {
			for (int i = 0; i < TIMES; i++) {
				plain[0]++; // 읽기 => +1 => 쓰기 ( 이 과정에서 다른 Thread 가 끼어들 수 있다. )
			}
		} );
		
		Thread b = new Thread( () -> {
			for (int i = 0; i < TIMES; i++) {
				plain[0]++; // 읽기 => +1 => 쓰기 ( 이 과정에서 다른 Thread 가 끼어들 수 있다. )
			}
		} );
		
		a.start();
		b.start();
		// @Test 를 수행하는 main thread 는 a, b thread 가 종료된 다음에 종료
		a.join();
		b.join();
		
		System.out.println("plain[0] final = " + plain[0] + ", expected =  " + (TIMES * 2));
		assertEquals(TIMES*2, plain[0]);
	}
	
	@Test
	void atomicInteger() throws Exception{
		AtomicInteger atomic = new AtomicInteger(0);
		
		Thread a = new Thread( () -> {
			for (int i = 0; i < TIMES; i++) {
				atomic.incrementAndGet(); // 원자적 + 1
			}
		} );
		
		Thread b = new Thread( () -> {
			for (int i = 0; i < TIMES; i++) {
				atomic.incrementAndGet(); // 원자적 + 1
			}
		} );
		
		a.start();
		b.start();
		// @Test 를 수행하는 main thread 는 a, b thread 가 종료된 다음에 종료
		a.join();
		b.join();
		
		System.out.println("AtomicInteger final = " + atomic.get() + ", expected =  " + (TIMES * 2));
		assertEquals(TIMES*2, atomic.get());		
	}	
}