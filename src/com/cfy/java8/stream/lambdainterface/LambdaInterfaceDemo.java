package com.cfy.java8.stream.lambdainterface;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class LambdaInterfaceDemo {

	/**
	 * Function 函数型接口, 有一个输入参数，有一个输出 只要是 函数型接口 可以 用 lambda表达式简化
	 */
	@Test
	public void testFunction() {
		// Function<T, R> T: 入参泛型， R: 返回泛型

//		Function<String, Integer> function = new Function<String, Integer>() {
//			@Override
//			public String apply(String str) {
//				return Integer.parseInt(str);
//			}
//		};
		Function<String, Integer> function = (str) -> {
			return Integer.parseInt(str);
		};
		System.out.println(function.apply("123"));
	}

	/**
	 * 断定型接口：有一个输入参数，返回值只能是 布尔值！
	 */
	@Test
	public void testPredicate() {
		// 判断字符串是否为空
//		Predicate<String> predicate = new Predicate<String>() {
//			@Override
//			public boolean test(String str) {
//				return str.isEmpty();
//			}
//		};
		Predicate<String> predicate = String::isEmpty;
//		Predicate<String> predicate = (str)-> {return str.isEmpty(); };
		System.out.println(predicate.test(""));
	}

	/**
	 * Consumer 消费型接口: 只有输入，没有返回值
	 */
	@Test
	public void testConsumer() {
//		Consumer<String> consumer = new Consumer<String>() {
//			@Override
//			public void accept(String str) {
//				System.out.println(str);
//			}
//		};
		Consumer<String> consumer = (str) -> {
			System.out.println(str);
		};
		consumer.accept("sdadasd");
	}

	/**
	 * Supplier 供给型接口 没有参数，只有返回值
	 */
	@Test
	public void testSupplier() {
//		Supplier<Integer> supplier = new Supplier<Integer>() {
//			@Override
//			public Integer get() {
//				System.out.println("get()");
//				return 1024;
//			}
//		};
		Supplier<Integer> supplier = ()->{ return 1024; };
//		IntSupplier supplier = ()->{ return 1024; };
//		System.out.println(supplier.getAsInt());
		System.out.println(supplier.get());
	}
}