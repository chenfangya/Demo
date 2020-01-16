package com.cfy.thread;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月16日---下午4:47:21
*@action
*/
public class CompletableFutureDemo1 {

	@Test
	public void test1() {
		System.out.println(getPrice("12"));
	}
	
	@Test
	public void test2() throws Exception {

		long start = System.nanoTime();
		Future<Double> futurePrice = getPriceAsync("my favorite product");//试图取得价格
		long invocationTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Invocation returned after " + invocationTime + " msecs");
		// 执行更多任务
		doSomethingElse();
		// 在计算商品价格的同时
		try {
			double price = futurePrice.get();//从Future对象中读取价格，如果价格未知，会发生阻塞
			System.out.printf("Price is %.2f%n", price);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");
	}

	
	@Test
	public void test3() throws Exception {

		long start = System.nanoTime();
		Future<Double> futurePrice = getPriceAsyncByFactory("my favorite product");//试图取得价格
		long invocationTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Invocation returned after " + invocationTime + " msecs");
		// 执行更多任务
		doSomethingElse();
		// 在计算商品价格的同时
		try {
			double price = futurePrice.get();//从Future对象中读取价格，如果价格未知，会发生阻塞
			System.out.printf("Price is %.2f%n", price);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");
	}

	
	/**
	 * 使用工厂方法
	 * @param product
	 * @return
	 */
	public Future<Double> getPriceAsyncByFactory(String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product));
	}

	
	/**
	 * 异步
	 * @param product
	 * @return
	 */
	public Future<Double> getPriceAsync(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread( () -> {//在另一个线程中以异步方式执行计算
			try {
				double price = calculatePrice(product);
				futurePrice.complete(price);//需长时间计算的任务结束并得出结果时，设置Future的返回值
			} catch (Exception e) {
				futurePrice.completeExceptionally(e);//否则就抛出导致失败的ExecutionException异常，完成这次Future操作
			}
		}).start();
		return futurePrice;//无需等待还没结束的计算，直接返回Future对象
	}

	
	public double getPrice(String product) {
		return calculatePrice(product);
	}
	
	private double calculatePrice(String product) {
		delay();
		Random random = new Random();
		return random.nextDouble() * product.charAt(0) + product.charAt(1);
	}
	
	/**
	模拟1秒钟延迟的方法
	*/
	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
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
