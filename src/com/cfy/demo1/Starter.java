package com.cfy.demo1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Starter {
	
	private static Object lock = new Object();
	
	public static void main(String[] args) {
		
		Cabinet cabinet = new Cabinet();
		ExecutorService es = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			final int storeNumber = i;
			es.execute(() -> {
				synchronized (lock) {
					User user = new User(cabinet, storeNumber);
					user.useCabinet();
					System.out.println("我是用户" + storeNumber + ",我存储的数字是：" + cabinet.getStoreNumber());
				}
//				User user = new User(cabinet, storeNumber);
//				user.useCabinet();
//				System.out.println("我是用户" + storeNumber + ",我存储的数字是：" + cabinet.getStoreNumber());
			});
		}
		es.shutdown();
	}
}