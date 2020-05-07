package com.cfy.thread.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class Test {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {
			test1();//5322
			test2();//4211
			test3();//84
	}

	// 普通程序员
	public static void test1() {
		Long sum = 0L;
		long start = System.currentTimeMillis();
		for (Long i = 1L; i <= 10_0000_0000; i++) {
			sum += i;
		}
		long end = System.currentTimeMillis();
		System.out.println(String.format("Thread is Name: %s, Method is : %s, sum : %s, 耗时： %s", Thread.currentThread().getName(), "test1", sum, (end - start)));
	}

	// 会使用ForkJoin
	public static void test2() throws ExecutionException, InterruptedException {
		long start = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinDemo(0L, 10_0000_0000L);
		ForkJoinTask<Long> submit = forkJoinPool.submit(task);// 提交任务
		Long sum = submit.get();
		long end = System.currentTimeMillis();
		System.out.println(String.format("Thread is Name: %s, Method is : %s, sum : %s, 耗时： %s", Thread.currentThread().getName(), "test2", sum, (end - start)));
	}

	public static void test3() {
		long start = System.currentTimeMillis();
		// Stream并行流 () (]
		long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);
		long end = System.currentTimeMillis();
		System.out.println(String.format("Thread is Name: %s, Method is : %s, sum : %s, 耗时： %s", Thread.currentThread().getName(), "test3", sum, (end - start)));
	}
}