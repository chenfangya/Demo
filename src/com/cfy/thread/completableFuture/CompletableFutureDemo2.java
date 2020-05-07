package com.cfy.thread.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月16日---下午4:47:21
*@action
*/
public class CompletableFutureDemo2 {

	@Test
	public void test1() {
		long start = System.nanoTime();
		System.out.println(findPricesByStream("LetsSaveBig"));
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");//Price returned after 4002 msecs
	}
	
	@Test
	public void test2() throws Exception {
		long start = System.nanoTime();
		System.out.println(findPricesParallelStream("LetsSaveBig"));
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");//Price returned after 1001 msecs
	}
	
	@Test
	public void test3() throws Exception {
		long start = System.nanoTime();
		System.out.println(findPricesByCompletableFuture("LetsSaveBig"));
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");//Price returned after 1002 msecs
	}
	
	@Test
	public void test4() throws Exception {
		long start = System.nanoTime();
		System.out.println(findPricesByCustomized("LetsSaveBig"));
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");//Price returned after 1003 msecs
	}
	
	/**
	 * 应用定制的执行器： 
	 * 调整线程池的大小 《Java并发编程实战》 Brian Goetz建议，线程池大小与处理器的利用率之比可以使用下面的公式进行估算：
	 * Nthreads = NCPU * UCPU * (1 + W/C) 其中：
	 * ❑NCPU是处理器的核的数目，可以通过Runtime.getRuntime().availableProcessors()得到
	 * ❑UCPU是期望的CPU利用率（该值应该介于0和1之间） 
	 * ❑W/C是等待时间与计算时间的比率 
	 * 
	 * @param product
	 * @return
	 */
	public List<String> findPricesByCustomized(String product) {
		List<String> shops = Arrays.asList("BestPrice", "LetsSaveBig", "MyFavoriteShop", "BuyItAll", "ShopEasy");
		
		final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), //创建一个线程池，线程池中线程的数 目为 100和商店数目二者中较小的一个值
				new ThreadFactory() {
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						t.setDaemon(true);//使用守护线程——这种方式不会阻止程序的关停
						return t;
					}
			});
		
		
		List<CompletableFuture<String>> priceFutures = shops.stream().map(
				shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop, getPrice(product)), executor))
				.collect(Collectors.toList());
		return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

	
	/**
	 * 使用CompletableFuture异步
	 * @param product
	 * @return
	 */
	public List<String> findPricesByCompletableFuture(String product) {
		List<String> shops = Arrays.asList("BestPrice", "LetsSaveBig", "MyFavoriteShop", "BuyItAll");
		List<CompletableFuture<String>> priceFutures =
				shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(//使用CompletableFuture以异步方式计算每种商品的价格
				() -> String.format("%s price is %.2f",
				shop, getPrice(product))))
				.collect(Collectors.toList());
		return priceFutures.stream()
						   .map(CompletableFuture::join)//等待所有异步操作结束
						   .collect(Collectors.toList());
	}

	
	
	/**
	 * 使用并行流
	 * @param product
	 * @return
	 */
	public List<String> findPricesParallelStream(String product) {
		List<String> shops = Arrays.asList("BestPrice", "LetsSaveBig", "MyFavoriteShop", "BuyItAll");
		return shops.parallelStream().map(shop -> String.format("%s price is %.2f", shop, getPrice(product)))
							 .collect(Collectors.toList());
	}


	/**
	 * 使用流
	 * @param product
	 * @return
	 */
	public List<String> findPricesByStream(String product) {
		List<String> shops = Arrays.asList("BestPrice", "LetsSaveBig", "MyFavoriteShop", "BuyItAll");
		return shops.stream().map(shop -> String.format("%s price is %.2f", shop, getPrice(product)))
							 .collect(Collectors.toList());
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
