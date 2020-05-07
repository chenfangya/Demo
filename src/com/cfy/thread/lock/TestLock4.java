package com.cfy.thread.lock;

import java.util.concurrent.TimeUnit;

public class TestLock4 {
	public static void main(String[] args) {
		// 两个对象的Class类模板只有一个，static，锁的是Class
		Phone4 phone1 = new Phone4();
		Phone4 phone2 = new Phone4();
		//锁的存在
		new Thread(() -> {
			phone1.sendSms();
		}, "A").start();
		// 捕获
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(() -> {
			phone2.call();
		}, "B").start();
	}
}

//Phone3唯一的一个 Class 对象
class Phone4 {
	//静态的同步方法 锁的是 Class 类模板
	public static synchronized void sendSms() {
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("发短信");
	}

	//普通的同步方法 锁的调用者
	public synchronized void call() {
		System.out.println("打电话");
	}
}