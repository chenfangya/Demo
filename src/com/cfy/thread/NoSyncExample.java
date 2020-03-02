package com.cfy.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class NoSyncExample {
	
	/**
	 * 栈封闭
	 * 多个线程访问同一个方法的局部变量时，不会出现线程安全问题，因为局部变量存储在虚拟机栈中，属于线程私有的。
	 */
	@Test
	public void test() {
		NoSyncExample example = new NoSyncExample();
	    ExecutorService executorService = Executors.newCachedThreadPool();
	    executorService.execute(() -> example.add100());
	    executorService.execute(() -> example.add100());
	    executorService.shutdown();
	}
	
	@Test
	public void test1() throws InterruptedException {
		ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
		Thread thread1 = new Thread(() -> {
			threadLocal.set(1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(String.format("thread1 ： %s , threadLocal : %s", Thread.currentThread().getName(), threadLocal.get()));
			threadLocal.remove();
		});
		Thread thread2 = new Thread(() -> {
			threadLocal.set(2);
			System.out.println(String.format("thread2 ： %s , threadLocal : %s", Thread.currentThread().getName(), threadLocal.get()));
			threadLocal.remove();
		});
		thread1.start();
		thread2.start();
		Thread.sleep(2000L);
	}
	
	
	@Test
	public void test2() {
		ThreadLocal<Integer> threadLocal1 = new ThreadLocal<>();
        ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();
        Thread thread1 = new Thread(() -> {
            threadLocal1.set(1);
            threadLocal2.set(1);
            System.out.println(String.format("threadName ： %s , threadLocal1 : %s", Thread.currentThread().getName(), threadLocal1.get()));
            System.out.println(String.format("threadName ： %s , threadLocal2 : %s", Thread.currentThread().getName(), threadLocal2.get()));
        });
        Thread thread2 = new Thread(() -> {
            threadLocal1.set(2);
            threadLocal2.set(2);
            System.out.println(String.format("threadName ： %s , threadLocal1 : %s", Thread.currentThread().getName(), threadLocal1.get()));
            System.out.println(String.format("threadName ： %s , threadLocal2 : %s", Thread.currentThread().getName(), threadLocal2.get()));
        });
        thread1.start();
        thread2.start();
	}
	
	
	public void add100() {
		int cnt = 0;
		for (int i = 0; i < 100; i++) {
			cnt++;
		}
		System.out.println(cnt);
	}
}