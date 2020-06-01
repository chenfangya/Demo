package com.cfy.design.single;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午3:10:04
 * @action
 *
 *         1. 分配内存空间 2、执行构造方法，初始化对象 3、把这个对象指向这个空间
 *
 *         123 132 A B // 此时lazyMan还没有完成构造
 */
public class InnerClassDemo {

	private InnerClassDemo() {

	}

	public static InnerClassDemo getInstace() {
		return InnerClass.HOLDER;
	}

	public static class InnerClass {
		private static final InnerClassDemo HOLDER = new InnerClassDemo();
	}
}
