package com.cfy.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static java.util.stream.Collectors.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.cfy.entity.Apple;
import com.cfy.entity.Dish;
import com.cfy.entity.Dish.Type;

/**
*@author    created by ChenFangYa
*@date  2020年1月17日---上午10:35:59
*@action	
*<U> U reduce(U identity,
*             BiFunction<U, ? super T, U> accumulator,
*             BinaryOperator<U> combiner);
*一个初始值；
*一个BinaryOperator<T>来将两个元素结合起来产生一个新值。
*第三个参数BinaryOperator<U> combiner 操作的对象是第二个参数BiFunction<U,? super T,U> accumulator的返回值，供多线程使用的
*/
public class ReduceDemo {
	
	String splitLine = "-----------------------------";
	
	static List<Apple> inventory = null;
	static List<Dish> menu = null;
	static Lock lock = null;
	
	
	static {
		lock = new ReentrantLock();
		
		inventory = new ArrayList<>();
		inventory.add(new Apple("yellow", 200.0D));
		inventory.add(new Apple("blue", 100.0D));
		inventory.add(new Apple("green", 300.0D));
		inventory.add(new Apple("red", 500.0D));
		
		menu = new ArrayList<>();
		menu.add(new Dish("A", true, 1000, Type.FISH));
		menu.add(new Dish("B", false, 2000, Type.MEAT));
		menu.add(new Dish("C", true, 3000, Type.OTHER));
	}

	@Test
	public void test () {
		 //accumulator不写入list,不需要线程同步,初始值使用普通的list
        List<Integer> list = new ArrayList<>();
        AtomicInteger accumulateCount = new AtomicInteger(0);
        AtomicInteger combineCount = new AtomicInteger(0);
        List<Integer> reduceResult = IntStream.range(0, 100)
                .parallel()
                .boxed()
                .reduce(list, (i, j) -> {
                	System.out.println(String.format("2-accumulator: i=: %s, j=:%s", i , j));
                    accumulateCount.incrementAndGet();
                    //不改变初始的i,而是返回一个新的i
                    List<Integer> newI = new ArrayList<>(i);
                    newI.add(j);
                    return newI;
                }, (i, j) -> {
                	System.out.println(String.format("3-combiner: i=: %s, j=:%s", i , j));
                    combineCount.incrementAndGet();
                    System.out.println(String.format("i==j: %s, thread name:%s", i == j, Thread.currentThread().getName()));
                    ArrayList<Integer> newI = new ArrayList<>(i);
                    newI.addAll(j);
                    return newI;
                });
        System.out.println("---------------------------------------");
        System.out.println(String.format("reduce result size: %s", reduceResult.size()));//reduce result size: 100
        System.out.println(String.format("reduce result : %s", reduceResult));// 0 -99
        System.out.println(String.format("accumulateCount: %s", accumulateCount.get()));//accumulateCount: 100
        System.out.println(String.format("combineCount: %s", combineCount.get()));//combineCount: 63
	}
	
	
	@Test
	public void test1 () {
		 List<Integer> list = Collections.synchronizedList(new ArrayList<>());

	        AtomicInteger atomicInteger = new AtomicInteger(0);
	        AtomicInteger atomicInteger2 = new AtomicInteger(0);
	        List<Integer> reduce = IntStream.range(0, 100)
	                .boxed()
	                .parallel()
	                .reduce(list, (i, j) -> {
	                	atomicInteger.incrementAndGet();
	                    i.add(j);
	                    return i;
	                }, (i, j) -> {
	                	atomicInteger2.incrementAndGet();
	                    System.out.println(String.format("i==j: %s, thread name:%s", i == j, Thread.currentThread().getName()));
	                    return i;
	                });

	        System.out.println(String.format("reduce.size： %s", reduce.size()));
	        System.out.println(String.format("atomicInteger： %s 次", atomicInteger.get()));
	        System.out.println(String.format("atomicInteger2： %s 次", atomicInteger2.get()));
	}
	
	
	/**
	 * 它需要三个参数：
	 * 
	 * 第一个参数是归约操作的起始值，也是流中没有元素时的返回值，所以很显然对于数值和而言0是一个合适的值。
	 * 第二个参数函数，将菜肴转换成一个表示其所含热量的int。
	 * 第三个参数是一个BinaryOperator，将两个项目累积成一个同类型的值。这里它就是对两个int求和。
	 */
	@Test
	public void test2 () {
		
		int totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
		
		//取最高
		menu.stream().collect(reducing(
				(d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).ifPresent(System.out::println);

		
		//用reduce方法来实现toListCollector所做的工作：
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6).parallelStream().reduce(
												new ArrayList<Integer>(),
												(List<Integer> l, Integer e) -> {
													l.add(e);
													return l; 
												},
												(List<Integer> l1, List<Integer> l2) -> {
														l1.addAll(l2);
														return l1; 
												}
											);

		System.out.println(String.format("totalCalories： %s", totalCalories));
		System.out.println(String.format("numbers： %s", numbers));
	}
}
