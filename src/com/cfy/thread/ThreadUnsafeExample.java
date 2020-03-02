package com.cfy.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUnsafeExample {
	
	public static void main(String[] args) throws InterruptedException {
	    final int threadSize = 1000;
	    ThreadUnsafeExample example = new ThreadUnsafeExample();
	    final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
	    ExecutorService executorService = Executors.newCachedThreadPool();
	    for (int i = 0; i < threadSize; i++) {
	        executorService.execute(() -> {
	            example.add();
	            example.addAtomic();
	            countDownLatch.countDown();
	        });
	    }
	    countDownLatch.await();
	    executorService.shutdown();
	    System.out.println(String.format("example.get() : %s", example.get()));
	    System.out.println(String.format("example.getAtomic() : %s", example.getAtomic()));
	}

	private int cnt = 0;
	private AtomicInteger atomicInteger = new AtomicInteger();

	public /* synchronized */ void add() {
		cnt++;
	}
	
	public /* synchronized */ void addAtomic() {
		atomicInteger.incrementAndGet();
	}

	public int get() {
		return cnt;
	}
	
	public int getAtomic() {
		return atomicInteger.get();
	}
}