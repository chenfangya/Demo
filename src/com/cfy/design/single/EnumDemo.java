package com.cfy.design.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午3:12:10
 * @action
 */
public enum EnumDemo {

	INSTANCE;

	public EnumDemo getInstance() {
		return INSTANCE;
	}
}

class Test {
	
	public static void main(String[] args)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		EnumDemo instance1 = EnumDemo.INSTANCE;
		Constructor<EnumDemo> declaredConstructor = EnumDemo.class.getDeclaredConstructor(String.class, int.class);
		declaredConstructor.setAccessible(true);
		EnumDemo instance2 = declaredConstructor.newInstance();
		// java.lang.IllegalArgumentException: Cannot reflectively create enum objects
		System.out.println(instance1);
		System.out.println(instance2);
	}
}
