package com.cfy.design.singleton;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午3:05:02
 * @action
 */
//饿汉式单例
public class HungrySingleton {

	private HungrySingleton() {
	}

	private static final  HungrySingleton HUNGRY = new HungrySingleton();

	public static HungrySingleton getInstance() {
		return HUNGRY;
	}
}
