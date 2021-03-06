package com.cfy.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月19日---下午4:37:59
*@action
*主要有三种 Executor：
*	CachedThreadPool：一个任务创建一个线程；
*	FixedThreadPool：所有任务只能使用固定大小的线程；
*	SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。
*
*	public ThreadPoolExecutor(
*							int corePoolSize, // 核心线程池大小 
*							int maximumPoolSize, // 最大核心线程池大小 
*							long keepAliveTime, // 超时了没有人调用就会释放
* 							TimeUnit unit, // 超时单位
*							BlockingQueue<Runnable> workQueue, // 阻塞队列 
*							ThreadFactory threadFactory, //线程工厂：创建线程的，一般 不用动 
*							RejectedExecutionHandler handle // 拒绝策略)
*@author 
 *
 */
public class ExecutorDemo {

	@Test
	public void test () {
//		ExecutorService threadPool = Executors.newSingleThreadExecutor();// 单个线程
//		 ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建一个固定的线程池的大小
		ExecutorService threadPool = Executors.newCachedThreadPool(); // 可伸缩的，遇强则强，遇弱则弱
		try {
			for (int i = 0; i < 100; i++) {
				// 使用了线程池之后，使用线程池来创建线程
				threadPool.execute(() -> {
					System.out.println(Thread.currentThread().getName() + " ok");
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 线程池用完，程序结束，关闭线程池
			threadPool.shutdown();
		}
		
	}
	
	/*
	*守护线程是程序运行时在后台提供服务的线程，不属于程序中不可或缺的部分。
	*
	*当所有非守护线程结束时，程序也就终止，同时会杀死所有守护线程。
	*
	*main() 属于非守护线程。
	*
	*在线程启动之前使用 setDaemon() 方法可以将一个线程设置为守护线程。
	 */
	@Test
	public void test1 () {
		Thread thread = new Thread(new MyRunnable());
	    thread.setDaemon(true);
	    thread.start();
	}
	
	@Test
	public void test2 () {
		ExecutorService executorService = Executors.newCachedThreadPool();
	    executorService.execute(() -> {
	        try {
	            Thread.sleep(2000);
	            System.out.println("Thread run");
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    });
//	    executorService.shutdownNow();//会等待线程都执行完毕之后再关闭，但是如果调用的是 shutdownNow() 方法，则相当于调用每个线程的 interrupt() 方法。
	    executorService.shutdown();
	    System.out.println("Main run");
	}
	
	@Test
	public void test3 () {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<?> submit = executorService.submit(() -> {
			try {
				Thread.sleep(2000);
				System.out.println("Thread run");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		submit.cancel(true);//中断 Executor 中的一个线程
		System.out.println("Main run");
	}
	
	class MyRunnable implements Runnable {

		@Override
		public void run() {
			System.out.println(String.format("%s - MyRunnable", Thread.currentThread().getName()));
		}
		
	}
}
