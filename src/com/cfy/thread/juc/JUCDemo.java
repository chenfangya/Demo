package com.cfy.thread.juc;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月19日---下午5:40:59
*@action
*	JUC提供了 Condition 类来实现线程之间的协调，
*		可以在 Condition 上调用 await() 方法使线程等待，
*		其它线程调用 signal() 或 signalAll() 方法唤醒等待的线程。
*
*		相比于 wait() 这种等待方式，await() 可以指定等待的条件，因此更加灵活。
*
*
*	CountDownLatch 用来控制一个或者多个线程等待多个线程。
*		维护了一个计数器 cnt，每次调用 countDown() 方法会让计数器的值减 1，
*		减到 0 的时候，那些因为调用 await() 方法而在等待的线程就会被唤醒。
*
*
*	CyclicBarrier	用来控制多个线程互相等待，只有当多个线程都到达时，这些线程才会继续执行。
*		通过维护计数器来实现的。线程执行 await() 方法之后计数器会减 1，
*		并进行等待，直到计数器为 0，所有调用 await() 方法而在等待的线程才能继续执行。	
*	CyclicBarrier 和 CountdownLatch 的一个区别是，CyclicBarrier 的计数器通过调用 reset() 方法可以循环使用，所以它才叫做循环屏障。
*	CyclicBarrier 有两个构造函数，其中 parties 指示计数器的初始值，barrierAction 在所有线程都到达屏障的时候会执行一次。
*
*
*	
*	Semaphore	类似于操作系统中的信号量，可以控制对互斥资源的访问线程数。
*
*
*	FutureTask	Callable 可以有返回值，返回值通过 Future 进行封装
*		FutureTask 实现了 RunnableFuture 接口，
*		该接口继承自 Runnable 和 Future 接口，这使得 FutureTask 既可以当做一个任务执行，也可以有返回值。
*
*
*	BlockingQueue	有以下阻塞队列的实现：
*		FIFO 队列 ：LinkedBlockingQueue、ArrayBlockingQueue（固定长度）  
*		优先级队列 ：PriorityBlockingQueue
*	提供了阻塞的 take() 和 put() 方法：如果队列为空 take() 将阻塞，直到队列中有内容；如果队列为满 put() 将阻塞，直到队列有空闲位置。
*	
*/
public class JUCDemo {
	
	private static Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private static Object obj = new Object();
    private static final int TOTALTHREAD = 10;
    private static final int CLIENTCOUNT = 3;
    private static final int TOTALREQUESTCOUNT = 10;
    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
    
    
    @Test
    public void test() {
    	ExecutorService executorService = Executors.newCachedThreadPool();
	    executorService.execute(this::after);
	    executorService.execute(this::before);
    }
    
    
    @Test
    public void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(TOTALTHREAD);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < TOTALTHREAD; i++) {
            executorService.execute(() -> {
                System.out.print("run..");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("end");
        executorService.shutdown();
    }
    
    
    @Test
    public void test2() {
         CyclicBarrier cyclicBarrier = new CyclicBarrier(TOTALTHREAD);
         ExecutorService executorService = Executors.newCachedThreadPool();
         for (int i = 0; i < TOTALTHREAD; i++) {
             executorService.execute(() -> {
                 System.out.println("before..");
                 try {
					cyclicBarrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
                 System.out.println("after..");
             });
         }
         executorService.shutdown();
    }
    
    
    /**
     * 模拟了对某个服务的并发请求，每次只能有 3 个客户端同时访问，请求总数为 10。
     */
    @Test
    public void test3() {
        Semaphore semaphore = new Semaphore(CLIENTCOUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < TOTALREQUESTCOUNT; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    System.out.print(semaphore.availablePermits() + " ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }
        executorService.shutdown();
    }
    
    
    /**
     * FutureTask 可用于异步获取执行结果或取消执行任务的场景
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test4() throws InterruptedException, ExecutionException {
    	FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 0;
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(10);
                    result += i;
                }
                return result;
            }
        });

        Thread computeThread = new Thread(futureTask);
        computeThread.start();

        Thread otherThread = new Thread(() -> {
            System.out.println("other task is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        otherThread.start();
        System.out.println(futureTask.get());
    }
    
    
    /**
     * BlockingQueue生产消费模型
     * @throws InterruptedException
     */
    @Test
    public void test5() throws InterruptedException {
    	
    	ExecutorService service = Executors.newCachedThreadPool();
    	
        Producer producer = new Producer(queue);
        Producer producer1 = new Producer(queue);
        service.execute(producer);
        service.execute(producer1);
        Consumer consumer = new Consumer(queue);
        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);
        Consumer consumer3 = new Consumer(queue);
        Consumer consumer4 = new Consumer(queue);
        service.execute(consumer);
        service.execute(consumer1);
        service.execute(consumer2);
        service.execute(consumer3);
        service.execute(consumer4);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Producer producer4 = new Producer(queue);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(producer4);
        
        Thread.sleep(2000L);
        producer.stop();
        producer1.stop();
        producer2.stop();
        producer3.stop();
        producer4.stop();
        Thread.sleep(2000L);
        service.shutdown();
    }
    
    
    /**
     * ForkJoin
     * 	主要用于并行计算中，和 MapReduce 原理类似，都是把大的计算任务拆分成多个小任务并行计算。
     * 
     * ForkJoinPool 实现了工作窃取算法来提高 CPU 的利用率。
     * 	每个线程都维护了一个双端队列，用来存储需要执行的任务。工作窃取算法允许空闲的线程从其它线程的双端队列中窃取一个任务来执行。
     * 	窃取的任务必须是最晚的任务，避免和队列所属线程发生竞争。但是如果队列中只有一个任务时还是会发生竞争。
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Test
    public void test6() throws InterruptedException, ExecutionException {
    	ForkJoinExample example = new ForkJoinExample(1, 10000);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> result = forkJoinPool.submit(example);//ForkJoin 使用 ForkJoinPool 来启动，它是一个特殊的线程池，线程数量取决于 CPU 核数。
        System.out.println(result.get());
    }
    
    
    static class ForkJoinExample extends RecursiveTask<Integer> {
		private static final long serialVersionUID = 3344211400701201054L;
		
		private static final int THRESHOLD = 5;
        private int first;
        private int last;

        public ForkJoinExample(int first, int last) {
            this.first = first;
            this.last = last;
        }

        @Override
        protected Integer compute() {
            int result = 0;
            if (last - first <= THRESHOLD) {
                // 任务足够小则直接计算
                for (int i = first; i <= last; i++) {
                    result += i;
                }
            } else {
                // 拆分成小任务
                int middle = first + (last - first) / 2;
                ForkJoinExample leftTask = new ForkJoinExample(first, middle);
                ForkJoinExample rightTask = new ForkJoinExample(middle + 1, last);
                leftTask.fork();
                rightTask.fork();
                result = leftTask.join() + rightTask.join();
            }
            return result;
        }
    }
    
    
	private static class Producer implements Runnable {

		private volatile boolean isRunning = true;// 是否在运行标志
		private BlockingQueue<String> queue;// 阻塞队列
		private static AtomicInteger count = new AtomicInteger();// 自动更新的值
		private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
		
		// 构造函数
		public Producer(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			String data = null;
			Random r = new Random();
			System.out.println("启动生产者线程！");
			try {
				while (isRunning) {
					System.out.println("正在生产数据...");
					Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));// 取0~DEFAULT_RANGE_FOR_SLEEP值的一个随机数
					data = "data:" + count.incrementAndGet();// 以原子方式将count当前值加1
					System.out.println("将数据：" + data + "放入队列...");
					if (!queue.offer(data, 2, TimeUnit.SECONDS)) {// 设定的等待时间为2s，如果超过2s还没加进去返回true
						System.out.println("放入数据失败：" + data);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				System.out.println("退出生产者线程！");
			}
		}

		public void stop() {
			isRunning = false;
		}
	}

	private static class Consumer implements Runnable {

		private BlockingQueue<String> queue;
		private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

		// 构造函数
		public Consumer(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			System.out.println("启动消费者线程！");
			Random r = new Random();
			boolean isRunning = true;
			try {
				while (isRunning) {
					System.out.println("正从队列获取数据...");
					String data = queue.poll(2, TimeUnit.SECONDS);// 有数据时直接从队列的队首取走，无数据时阻塞，在2s内有数据，取走，超过2s还没数据，返回失败
					if (null != data) {
						System.out.println("拿到数据：" + data);
						System.out.println("正在消费数据：" + data);
						Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
					} else {
						// 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
						isRunning = false;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				System.out.println("退出消费者线程！");
			}
		}
	}
    
    
    

    public void before() {
        lock.lock();
        try {
            System.out.println("before");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void after() {
        lock.lock();
        try {
            condition.await();
            System.out.println("after");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
