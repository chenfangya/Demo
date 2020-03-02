package com.cfy.thread.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * @author created by ChenFangYa
 * @date 2020年1月19日---下午4:58:50
 * @action
 */
public class SynchronizedDemo {
	
	private static final Object obj = new Object();

	@Test
	public void test() {
		ExecutorService executorService = Executors.newCachedThreadPool();
//		executorService.execute(this::func1);
//		executorService.execute(this::func1);
//		executorService.execute(new SynchronizedDemo()::func3);
//		executorService.execute(new SynchronizedDemo()::func3);
		executorService.execute(SynchronizedDemo::func4);
		executorService.execute(SynchronizedDemo::func4);
	}

	public void func() {
		synchronized (obj) {
			for (int i = 0; i < 10; i++) {
				System.out.println(String.format("THread-name --  %s, i = %s and obj = %s", Thread.currentThread().getName(), i, obj));
			}
		}
	}
	
	public void func1() {
		synchronized (this) {//它只作用于同一个对象，如果调用两个对象上的同步代码块，就不会进行同步。
			for (int i = 0; i < 10000; i++) {
				System.out.println(i + " ");
			}
		}
	}
	
	public synchronized void func2 () {//作用于同一个对象。
		for (int i = 0; i < 10; i++) {
			System.out.print(i + " ");
		}
	}
	
	public void func3() {
	    synchronized (SynchronizedDemo.class) {//作用于整个类，也就是说两个线程调用同一个类的不同对象上的这种同步语句，也会进行同步。
	    	for (int i = 0; i < 1000; i++) {
				System.out.println(i + " ");
			}
	    }
	}
	
	
	public synchronized static void func4() {//作用于整个类。
	    for (int i = 0; i < 1000; i++) {
				System.out.println(i + " ");
	    }
	}
}
