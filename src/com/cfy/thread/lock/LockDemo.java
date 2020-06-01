package com.cfy.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.Test;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午4:13:13
 * @action
 */
public class LockDemo {

	@Test
	public void test1() {
		Phone5 phone5 = new Phone5();
		Phone6 phone6 = new Phone6();
		new Thread(() -> {
			phone5.sms();
			phone6.sms();
		}, "A").start();
		new Thread(() -> {
			phone5.sms();
			phone6.sms();
		}, "B").start();
	}

	@Test
	public void test2() throws InterruptedException {
		// ReentrantLock reentrantLock = new ReentrantLock();
		// reentrantLock.lock();
		// reentrantLock.unlock();
		// 底层使用的自旋锁CAS
		SpinlockDemo lock = new SpinlockDemo();
		new Thread(() -> {
			lock.myLock();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.myUnLock();
			}
		}, "T1").start();
		TimeUnit.SECONDS.sleep(1);
		new Thread(() -> {
			lock.myLock();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.myUnLock();
			}
		}, "T2").start();
	}
}

class Phone5 {
	public synchronized void sms() {
		System.out.println(String.format("Phone5 : Thread.currentThread : %s, sms", Thread.currentThread().getName()));
		call(); // 这里也有锁
	}

	public synchronized void call() {
		System.out.println(String.format("Phone5 : Thread.currentThread : %s, call", Thread.currentThread().getName()));
	}
}

class Phone6 {

	Lock lock = new ReentrantLock();

	public void sms() {
		lock.lock(); // 细节问题：lock.lock(); lock.unlock(); // lock 锁必须配对，否则就会死在里面
		lock.lock();
		try {
			System.out.println(
					String.format("Phone6 : Thread.currentThread : %s, sms", Thread.currentThread().getName()));
			call(); // 这里也有锁
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
			lock.unlock();
		}
	}

	public void call() {
		lock.lock();
		try {
			System.out.println(
					String.format("Phone6 : Thread.currentThread : %s, call", Thread.currentThread().getName()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}