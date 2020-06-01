package com.cfy.thread.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.jupiter.api.Test;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午3:16:41
 * @action
 */

/**
 * CAS compareAndSet : 比较并交换！
 * 
 * 自旋锁 CAS ： 比较当前工作内存中的值和主内存中的值，如果这个值是期望的，那么则执行操作！如果不是就一直循环！ 缺点： 1、 循环会耗时
 * 2、一次性只能保证一个共享变量的原子性 3、ABA问题
 * 
 * @author XDF
 *
 */
public class CASDemo {

	// AtomicStampedReference 注意，如果泛型是一个包装类，注意对象的引用问题 正常在业务操作，这里面比较的都是一个个对象
	static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
	private static AtomicReference<Integer> ar = new AtomicReference<>(0);
	
	@Test
	public void test1() {
		AtomicInteger atomicInteger = new AtomicInteger(2020);
		// 期望、更新
		// public final boolean compareAndSet(int expect, int update)
		// 如果我期望的值达到了，那么就更新，否则，就不更新, CAS 是CPU的并发原语！
		System.out.println(atomicInteger.compareAndSet(2020, 2021));
		System.out.println(atomicInteger.get());
		atomicInteger.getAndIncrement();
		System.out.println(atomicInteger.compareAndSet(2020, 2021));
		System.out.println(atomicInteger.get());
	}

	@Test
	public void test2() throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					while (true) {
						Integer temp = ar.get();
						if (ar.compareAndSet(temp, temp + 1))
							break;
					}
				}
					
			}) .start();
		}
		TimeUnit.SECONDS.sleep(10);
		System.out.println(ar.get()); // 10000000
	}

	@Test
	public void test3() throws InterruptedException {
		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp(); // 获得版本号
			System.out.println(String.format("ThreadName: %s, a1 : %s, Reference : %s", Thread.currentThread().getName(), stamp, atomicStampedReference.getReference()));
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			atomicStampedReference.compareAndSet(1, 2, atomicStampedReference.getStamp(),
					atomicStampedReference.getStamp() + 1);
			
			System.out.println(String.format("ThreadName: %s, a2 : %s, Reference : %s", Thread.currentThread().getName(),
					atomicStampedReference.getStamp(), atomicStampedReference.getReference()));
			System.out.println(String.format("ThreadName: %s, falg: %s, Reference : %s", Thread.currentThread().getName(),
					atomicStampedReference.compareAndSet(2, 1, atomicStampedReference.getStamp(),
							atomicStampedReference.getStamp() + 1), atomicStampedReference.getReference()));
			System.out.println(String.format("ThreadName: %s, a3=> : %s, Reference : %s", Thread.currentThread().getName(),
					atomicStampedReference.getStamp(), atomicStampedReference.getReference()));

		}, "a").start();
		// 乐观锁的原理相同！
		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp(); // 获得版本号
			System.out.println("b1=>" + stamp);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(String.format("ThreadName: %s, falg: %s, Reference : %s", Thread.currentThread().getName(),
					atomicStampedReference.compareAndSet(1, 6, stamp, stamp + 1), atomicStampedReference.getReference()));
			System.out.println(String.format("ThreadName: %s, b2=> : %s, Reference : %s", Thread.currentThread().getName(),
					atomicStampedReference.getStamp(), atomicStampedReference.getReference()));
		}, "b").start();

		TimeUnit.SECONDS.sleep(3);
	}
}
