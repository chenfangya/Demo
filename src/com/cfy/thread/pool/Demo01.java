package com.cfy.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 拒绝策略
 * new ThreadPoolExecutor.AbortPolicy() // 银行满了，还有人进来，不处理这个人的，抛出异 常 new
 * ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里！ new
 * ThreadPoolExecutor.DiscardPolicy() //队列满了，丢掉任务，不会抛出异常！ new
 * ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争，也不会 抛出异常！
 */
public class Demo01 {
	public static void main(String[] args) {
		// 自定义线程池！工作 ThreadPoolExecutor
		// 最大线程到底该如何定义
		// 1、CPU 密集型，几核，就是几，可以保持CPu的效率最高！
		// 2、IO 密集型 > 判断你程序中十分耗IO的线程，
		// 程序 15个大型任务 io十分占用资源！
		
		// 获取CPU的核数
		System.out.println(Runtime.getRuntime().availableProcessors());
		ExecutorService threadPool = new ThreadPoolExecutor(
				2, // 核心线程池大小 
				Runtime.getRuntime().availableProcessors(), // 最大核心线程池大小 
				3, // 超时了没有人调用就会释放
				TimeUnit.SECONDS, // 超时单位
				new LinkedBlockingDeque<>(3),// 阻塞队列 
				Executors.defaultThreadFactory(), //线程工厂：创建线程的，一般 不用动 
				new ThreadPoolExecutor.DiscardOldestPolicy()  // 拒绝策略
				); // 队列满了，尝试去和最早的竞争，也不会抛出异常！
		try {
			// 最大承载：Deque + max
			// 超过 RejectedExecutionException
			for (int i = 1; i <= 9; i++) {
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
}