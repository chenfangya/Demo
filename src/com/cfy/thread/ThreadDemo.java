package com.cfy.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月19日---上午11:42:41
*@action
*synchronize关键字用法：
*	指定加锁对象：对给定对象加锁，进入同步代码前要获得给定对象的锁。
*	直接作用于实例方法：相当于对当前实例加锁，进入同步代码前要获得当前实例的锁。
*	直接作用于静态方法：相当于对当前类加锁，进入同步代码前要获得当前类的锁。
*
*Thread.sleep(millisec) 方法会休眠当前正在执行的线程，millisec 单位为毫秒。可能会抛出 InterruptedException，
*	因为异常不能跨线程传播回 main() 中，因此必须在本地进行处理。抛出的其它异常也同样需要在本地进行处理。
*
*Thread.yield() 的调用声明了当前线程已经完成了生命周期中最重要的部分，可以切换给其它线程来执行。
*	该方法只是对线程调度器的一个建议，而且也只是建议具有相同优先级的其它线程可以运行。
*
* interrupt() 中断该线程，如果该线程处于阻塞、限期等待或者无限期等待状态，
* 	那么就会抛出 InterruptedException，从而提前结束该线程。
* 	但是不能中断 I/O 阻塞和 synchronized 锁阻塞。
* 
* interrupted() 调用 interrupt() 方法会设置线程的中断标记，此时调用 interrupted() 方法会返回 true。
* 
* join()	将当前线程挂起，而不是忙等待，直到目标线程结束。
* 
* wait() 是 Object 的方法，而 sleep() 是 Thread 的静态方法； 
* 	wait() 会释放锁，sleep() 不会。
*/
public class ThreadDemo {
	
	static final Object object = new Object();
	public static volatile int i = 0;
	static Lock lock = new ReentrantLock();
	public final static int MIN_PRIORITY = 1;
	public final static int NORM_PRIORITY = 5;
	public final static int MAX_PRIORITY = 10;

	
	@Test
	public void test() throws InterruptedException {
		Thread t1 = new Thread(){
			
		    @Override
		    public void run(){
		        System.out.println("Hello, I am t1");
		    }
		};
		t1.start();
		
		Thread t2 = new Thread(new CreateThread3());
        t2.start();
        Thread.sleep(2000L);
        t2.interrupt();// 中断线程（通知目标线程中断，也就是设置中断标志位，中断标志位表示当前线程已经被中断了。）
        System.out.println(String.format("t2.isInterrupted(): %s", t2.isInterrupted()));// 判断是否被中断（通过检查中断标志位）
        System.out.println(String.format("Thread.interrupted(): %s", Thread.interrupted()));// 判断是否被中断，并清除当前中断状态
     
	}
	
	
    /**
     *虽然对 t1 进行了中断，但是在 t1 中并没有中断处理的逻辑，因此，即使 t1 线程被置上了中断状态，但是这个中断不会发生任何作用。
     */
	@Test
	public void test1() throws Exception {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while (true) {
					System.out.println("Interruted!");
					Thread.yield();//使当前线程让出CPU。（当前线程在让出 CPU 后，还会进行 CPU 资源的争夺）
				}
			}
		};
		t1.start();
		Thread.sleep(2000);
		t1.interrupt();
	}
	
	 
     /**
     *t1在中断后处理一下，才会增加相应的中断处理代码
     */
	@Test
	public void test2() throws Exception {

		Thread t1 = new Thread() {
			@Override
			public void run() {
				while (true) {
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interruted!");
						break;
					}
					Thread.yield();
				}
			}
		};

		t1.start();
		Thread.sleep(2000);
		t1.interrupt();
	}
	
	
	
    /**
    *当线程在 sleep()休眠时，如果被中断，会产生InterruptedException异常。
    *Thread.sleep()方法由于中断而抛出异常，此时，它会清除中断标记，如果不加处理，那么在下一次循环开始时，就无法捕获这个中断，故在异常处理中，再次设置中断标记位。
    * 输出：Interruted When Sleep
    *      Interruted!
    */
	@Test
	public void test3() throws Exception {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while (true) {
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interruted!");
						break;
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						System.out.println("Interruted When Sleep");
						// 设置中断状态
						Thread.currentThread().interrupt();
					}
					Thread.yield();
				}
			}
		};
		t1.start();
		Thread.sleep(2000);
		t1.interrupt();
	}
	
	

	/**
	 *  wait() 方法会释放目标对 象的锁，而Thread.sleep()方法不会释放任何资源。
	 */
	@Test
	public void test4() {
		
		Thread t1 = new T1();
		Thread t2 = new T2();
		t1.start();
		t2.start();
	}
	
	
	/**
	 * join()方法其实是让调用线程在当前线程对象上进行等待。当线程执行完成后，被等待的线程会在退出前调用 notifyAll() 通知所有的等待线程继续执行。
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void test5() throws InterruptedException {
		
		AddThread at = new AddThread();
        at.start();
//        at.join();//表示无限等待，它会一直阻塞当前线程，直到目标线程执行完毕。
        at.join(1L);//给出了一个最大等待时间，如果超过给定时间目标线程还在执行，当前线程也会因为“等不及了”，而继续往下执行。
        System.out.println(i);//10000，若不适用join()可能输出0或者很小的数字，用join()，则当前线程需等待AddThread执行完毕
	}
	
	
	@Test
	public void test6() throws InterruptedException {
		
		Thread[] threads = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread(new PlusTask());
			threads[i].start();
		}
		for (int i = 0; i < 10; i++) 
			threads[i].join();
		System.out.println(i);//输出跳动  结果不定， 但大概率小于100000， 因为 i++是线程不安全的操作。
	}
	
	/**
	 * 线程组
	 * @throws InterruptedException 
	 */
	@Test
	public void test7() throws InterruptedException {
		ThreadGroup tg = new ThreadGroup("PrintGroup");//建立名为PrintGroup的线程组
        //将T1、T2加入到线程组并命名（线程命名最好有意义）
		Thread t1 = new Thread(tg, new ThreadGroupName(), "T1");
		Thread t2 = new Thread(tg, new ThreadGroupName(), "T2");
		t1.start();
		t2.start();
		System.out.println(tg.activeCount());//获得活动线程的总数
		tg.list();//打印线程组的中所有的线程信息
	    Thread.sleep(5000);
//	    tg.stop();//停止线程组所有线程（有Thread.stop()一样的问题)）
	}
	
	
	/**
	 * 守护线程（Daemon） 守护线程是一种特殊的线程，它是系统的守护者，在后台默默地完成一些系统性的服务，
	 *  比如垃圾回收线程、 JIT线程就可以理解为守护线程。
	 * 用户线程可以认为是系统的工作线程，如果用户线程全部结束，这也意味着这个程序实际上无事可做了。
	 * 守护线程要守护的对象已经不存在了，那么整个应用程序就自然应该结束。
	 * 因此当一个 Java 应用内，只有守护线程时， Java虚拟机就会自然退出。
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void test8() throws InterruptedException  {
		Thread t = new DaemonT();
		t.setDaemon(true);//将线程t设置为守护线程（必须在线程t  start之前设置，否则主线程会报错，t则正常运行，但会被当做普通线程）
		t.start();

		Thread.sleep(2000);
	}
	
	
	@Test
	public void test9() throws InterruptedException  {
		Thread high = new HightPriority();
		LowPriority low = new LowPriority();
		high.setPriority(Thread.MAX_PRIORITY);
		low.setPriority(Thread.MIN_PRIORITY);
		low.start();
		high.start();
		Thread.sleep(2000);
	}
	
	static class LowPriority extends Thread {
		static int count = 0;

		public void run() {
			while (true) {
				synchronized (ThreadDemo.class) {
					count++;
					if (count > 10000000) {
						System.out.println("LowPriority is complete");
						break;
					}
				}
			}
		}
	}
	static class HightPriority extends Thread {
		static int count = 0;

		public void run() {
			while (true) {
				synchronized (ThreadDemo.class) {
					count++;
					if (count > 10000000) {
						System.out.println("HightPriority is complete");
						break;
					}
				}
			}
		}
	}
	
	
	static class DaemonT extends Thread {
		public void run() {
			while (true) {
				System.out.println("I am alive");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class ThreadGroupName implements Runnable {

		@Override
		public void run() {
			String groupAndName = Thread.currentThread().getThreadGroup().getName() + "-"
					+ Thread.currentThread().getName();
			while (true) {
				System.out.println("I am " + groupAndName);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class PlusTask implements Runnable {

		@Override
		public void run() {
//			lock.lock();
			try {
			for (int k = 0; k < 10000; k++)
					i++;
			} finally {
//				lock.unlock();
			}
		}
	}
	
    static class AddThread extends Thread{
        @Override
        public void run() {
            for(i = 0; i < Integer.MAX_VALUE; i++);
        }
    }
	
	
	
	static class T1 extends Thread {
		
		@Override
		public void run() {
			synchronized (object) {
				System.out.println(System.currentTimeMillis() + ":T1 start!");
				try {
					System.out.println(System.currentTimeMillis() + ":T1 wait for object ");
					object.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() + ":T1 end!");
			}
		}
	}

	static class T2 extends Thread {
		
		@Override
		public void run() {
			synchronized (object) {
				System.out.println(System.currentTimeMillis() + ":T2 start!notify one thread");
				object.notify();
				System.out.println(System.currentTimeMillis() + ":T2 end!");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	static class CreateThread3 implements Runnable {
		
	    @Override
	    public void run() {
	    	try {
				Thread.sleep(2000L);
				System.out.println("Oh, I am sleep");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        System.out.println("Oh, I am Runnable");
	    }
	}
}
