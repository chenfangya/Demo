package com.cfy.design.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午3:05:58
 * @action
 */

//懒汉式单例
public class LazyDemo {

	private static boolean flag = false;

	private LazyDemo() {
		synchronized (LazyDemo.class) {
			if (flag == false) {
				flag = true;
			} else {
				throw new RuntimeException("不要试图使用反射破坏异常");
			}

		}
	}

	private static volatile LazyDemo lazy;

	// 双重检测锁模式的 懒汉式单例 DCL懒汉式
	public static LazyDemo getInstance() {
		if (lazy == null) {
			synchronized (LazyDemo.class) {
				if (lazy == null) {
					lazy = new LazyDemo(); // 不是一个原子性操作
				}
			}
		}
		return lazy;
	}

	// 反射！
	public static void main(String[] args) throws Exception {
		// LazyDemo instance = LazyDemo.getInstance();
		Field flag = LazyDemo.class.getDeclaredField("flag");
		flag.setAccessible(true);
		Constructor<LazyDemo> declaredConstructor = LazyDemo.class.getDeclaredConstructor(null);
		declaredConstructor.setAccessible(true);
		LazyDemo instance = declaredConstructor.newInstance();
		flag.set(instance, false);
		LazyDemo instance2 = declaredConstructor.newInstance();
		System.out.println(instance);
		System.out.println(instance2);
	}
}
