package com.cfy.leecode.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


public class FooBar {
	private int n;
	private CountDownLatch a;
	private CyclicBarrier barrier;// 使用CyclicBarrier保证任务按组执行

	public FooBar(int n) {
		this.n = n;
		a = new CountDownLatch(1);
		barrier = new CyclicBarrier(2);// 保证每组内有两个任务
	}

	public void foo() throws InterruptedException {

		try {
			for (int i = 0; i < n; i++) {
				System.out.print("foo");
				a.countDown();// printFoo方法完成调用countDown
				barrier.await();// 等待printBar方法执行完成
			}
		} catch (Exception e) {
		}
	}

	public void bar() throws InterruptedException {

        try {
            for (int i = 0; i < n; i++) {
                a.await();// 等待printFoo方法先执行
                System.out.println("bar");
                a = new CountDownLatch(1); // 保证下一次依旧等待printFoo方法先执行
                barrier.await();// 等待printFoo方法执行完成
            }
        } catch(Exception e) {}
    }
}