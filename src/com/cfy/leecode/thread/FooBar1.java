package com.cfy.leecode.thread;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class FooBar1 {
	private int n;
	// here is the full path, or maybe cann't compile in leetcode.
	Semaphore semaphoreFoo = new Semaphore(0);
	Semaphore semaphoreBar = new Semaphore(0);

	public FooBar1(int n) {
        this.n = n;
    }

	public void foo() throws InterruptedException {

		for (int i = 0; i < n; i++) {
			System.out.print("foo");
			// 由于下面阻塞了，所以这里变为0，下面的方法就能继续执行
			semaphoreBar.release();
			// 这里让他等一会，等到bar()执行完
			semaphoreFoo.acquire();
		}
	}

	public void bar() throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // 进来先变为1，就会等上面的release()使他变为0，才进行，所以肯定在foo之后。
            semaphoreBar.acquire();
            System.out.println("bar");
            //bar()执行完了，就让foo()继续。
            semaphoreFoo.release();
        }
    }
	
	public static void main(String[] args) {
		String a = "abca";
		
	}
}