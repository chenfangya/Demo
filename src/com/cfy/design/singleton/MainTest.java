package com.cfy.design.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * @author created by ChenFangYa
 * @date 2020-9-21---9:47:52
 * @action
 */
public class MainTest {

	@Test
	public void testHungrySingleton() {
		HungrySingleton instance = HungrySingleton.getInstance();
		HungrySingleton instance2 = HungrySingleton.getInstance();
		System.out.println(instance);
		System.out.println(instance2);
		assertEquals(instance, instance2);
	}

	@Test
	public void testLazySingleton() {
		LazySingleton instance = LazySingleton.getInstance();
		LazySingleton instance2 = LazySingleton.getInstance();
		System.out.println(instance);
		System.out.println(instance2);
		assertEquals(instance, instance2);
	}
	
	@Test
	public void testEnumSingleton() {
		Student instance = EnumSingleton.INSTANCE.getStudent();
		Student instance2 = EnumSingleton.INSTANCE.getStudent();
		System.out.println(instance);
		System.out.println(instance2);
		assertEquals(instance, instance2);
	}
	
	@Test
	public void testInnerClassSingleton() {
		InnerClassSingleton instance = InnerClassSingleton.getInstace();
		InnerClassSingleton instance2 = InnerClassSingleton.getInstace();
		System.out.println(instance);
		System.out.println(instance2);
		assertEquals(instance, instance2);
	}
}
