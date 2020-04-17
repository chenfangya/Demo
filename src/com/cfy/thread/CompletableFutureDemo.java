package com.cfy.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月16日---下午4:47:21
*@action
*/
public class CompletableFutureDemo {

	@Test
	public void test1() {
		ExecutorService executor = Executors.newCachedThreadPool();
//		Future<Double> future = executor.submit(new Callable<Double>() {//向ExecutorService提交一个Callable对象
//			public Double call() {
//				return doSomeLongComputation();//以异步方式在新的线程中执行耗时的操作
//			}
//		});
		
		Future<Double> future = executor.submit(this::doSomeLongComputation);
		
		doSomethingElse();//异步操作进行的同时，你可以做其他的事情
		try {
			//获取异步操作的结果， 如果最终被阻塞， 无法得到结果， 那么在最多等待1秒钟之后退出
			Double double1 = future.get(3, TimeUnit.SECONDS);
			System.out.println(double1);
		} catch (ExecutionException ee) {
			// 计算抛出一个异常
		} catch (InterruptedException ie) {
			// 当前线程在等待过程中被中断
		} catch (TimeoutException te) {
			// 在Future对象完成之前超过已过期
		}
	}
	
	public Double doSomeLongComputation() {
		System.out.println("doSomeLongComputation");
		try {
			Thread.sleep(2000);
			System.out.println("doSomeLongComputation------------END");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 1.0;
	}
	
	public void doSomethingElse() {
		System.out.println("doSomethingElse");
		try {
			Thread.sleep(2000);
			System.out.println("doSomethingElse------------END");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
