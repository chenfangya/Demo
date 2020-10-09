package com.cfy.design.singleton;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午3:05:58
 * @action 懒汉式单例
 */
public class LazySingleton {
	
	private static volatile LazySingleton instance = null;

	private LazySingleton() {}

	/**
	 * 双重检测锁模式的 懒汉式单例 DCL懒汉式
	 * @return
	 */
	public static LazySingleton getInstance() {
		if (instance == null) {
			synchronized (LazySingleton.class) {
				if (instance == null) {
					// 不是一个原子性操作
					instance = new LazySingleton();
				}
			}
		}
		return instance;
	}
}
