package com.cfy.design.single;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午3:05:02
 * @action
 */
//饿汉式单例
public class HungryDemo {

	// 可能会浪费空间
	private byte[] data1 = new byte[1024 * 1024];
	private byte[] data2 = new byte[1024 * 1024];
	private byte[] data3 = new byte[1024 * 1024];
	private byte[] data4 = new byte[1024 * 1024];

	private HungryDemo() {
	}

	private static final  HungryDemo HUNGRY = new HungryDemo();

	public static HungryDemo getInstance() {
		return HUNGRY;
	}
}
