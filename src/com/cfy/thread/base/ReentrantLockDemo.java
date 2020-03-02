package com.cfy.thread.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * @author created by ChenFangYa
 * @date 2020年1月19日---下午5:34:45
 * @action
 * 	synchronized 是 JVM 实现的，而 ReentrantLock 是 JDK 实现的。
 * 	新版本 Java 对 synchronized 进行了很多优化，例如自旋锁等，synchronized 与 ReentrantLock 大致相同。
 * 
 * 
 *	 等待可中断: 当持有锁的线程长期不释放锁的时候，正在等待的线程可以选择放弃等待，改为处理其他事情。
 * 			  ReentrantLock 可中断，而 synchronized 不行。
 * 
 * 
 * 	公平锁是指多个线程在等待同一个锁时，必须按照申请锁的时间顺序来依次获得锁。
 * 	synchronized 中的锁是非公平的，ReentrantLock 默认情况下也是非公平的，但是也可以是公平的。
 * 
 * 
 * 
 * 	一个 ReentrantLock 可以同时绑定多个 Condition 对象。
 */
public class ReentrantLockDemo {

	private Lock lock = new ReentrantLock();

	@Test
	public void test() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new ReentrantLockDemo():: func);
		executorService.execute(new ReentrantLockDemo():: func);
	}

	public void func() {
		lock.lock();
		try {
			for (int i = 0; i < 1000; i++) {
				System.out.println(i + " ");
			}
		} finally {
			lock.unlock(); // 确保释放锁，从而避免发生死锁。
		}
	}
}
