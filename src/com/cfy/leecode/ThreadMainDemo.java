package com.cfy.leecode;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.cfy.leecode.thread.Foo;
import com.cfy.leecode.thread.FooBar;
import com.cfy.leecode.thread.FooBar1;

/**
*@author    created by ChenFangYa
*@date  2020年6月2日---下午4:56:03
*@action
*/
public class ThreadMainDemo {

	/**
	 * 顺序打印
	 */
	@Test
	public void test1() throws InterruptedException {
		Foo f1 = new Foo();
//		Foo1 f1 = new Foo1();
//		Foo2 f1 = new Foo2();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					f1.first();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					f1.third();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					f1.second();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * 交替打印
	 */
	@Test
	public void test2() throws InterruptedException {
		FooBar f1 = new FooBar(5);
//		FooBar1 f1 = new FooBar1(5);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					f1.bar();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					f1.foo();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
}
