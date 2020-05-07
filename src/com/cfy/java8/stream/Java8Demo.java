package com.cfy.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Comparator.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.concurrent.locks.Lock;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.cfy.entity.Apple;
import com.cfy.entity.Dish;
import com.cfy.entity.Dish.Type;

/**
*@author    created by ChenFangYa
*@date  2020年1月16日---下午2:02:01
*@action
*/
public class Java8Demo {
	
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
	
	
	/**
	 * 排序
	 */
	@Test
	public void test() {
		
		System.out.println(inventory);
		
		//方法引用
		inventory.sort(comparing(Apple :: getWeight));
		
		
		
		inventory.sort((Apple a1, Apple a2)
				 -> a1.getWeight().compareTo(a2.getWeight())
				);

		
		inventory.sort(comparing(Apple::getWeight).reversed());//逆序
		
		
		inventory.sort(comparing(Apple::getWeight).reversed()//逆序
				.thenComparing(Apple::getColor));//一样重时按颜色排序
		
		System.out.println(inventory);

	}
	
	
	/**
	 * 谓词复合 and or 非  等
	 * 
	 * a.or(b).and(d)可以看作(a || b) && d
	 * 
	 */
	@Test
	public void test1() {
		
	   Predicate<Apple> redApple = a -> "red".equals(a.getColor());
	   
	   // 可以使用negate方法来返回一个Predicate的非，比如苹果不是红的
	   Predicate<Apple> notRedApple = redApple.negate();//产生现有Predicate对象redApple的非
	   
	   //可能想要把两个Lambda用and方法组合起来，比如一个苹果既是红色又比较重：
	   Predicate<Apple> redAndHeavyApple = redApple.and(a -> a.getWeight() > 150);
	   
	   //可以进一步组合谓词，表达要么是重（150克以上）的红苹果，要么是绿苹果
	   Predicate<Apple> redAndHeavyAppleOrGreen = redApple.and(a -> a.getWeight() > 150)
	   													  .or(a -> "green".equals(a.getColor())
	   											  );
	   
	   inventory.stream().filter(redApple).forEach(System.out::println);
	   System.out.println(splitLine);
	   inventory.stream().filter(notRedApple).forEach(System.out::println);
	   System.out.println(splitLine);
	   inventory.stream().filter(redAndHeavyApple).forEach(System.out::println);
	   System.out.println(splitLine);
	   inventory.stream().filter(redAndHeavyAppleOrGreen).forEach(System.out::println);
	}
	
	
	
	/**
	 * 函数复合
	 * andThen方法会返回一个函数，它先对输入应用一个给定函数，再对输出应用另一个函数。数学上会写作g(f(x))
	 * 
	 * compose方法，先把给定的函数用作compose的参数里面给的那个函数，然后再把函数本身用于结果。数学会写作f(g(x))
	 */
	@Test
	public void test2() {
		Function<Integer, Integer> f = x -> x + 1;//函数f给数字加1
		Function<Integer, Integer> g = x -> x * 2;//函数g给数字乘2

		//f&g 组合成一个函数h
		Function<Integer, Integer> h = f.andThen(g);//先给数字加1，再给结果乘2
		
		int result = h.apply(1);//return 4
		
		System.out.println(result);
		
		
		Function<Integer, Integer> f1 = x -> x + 1;//函数f给数字加1
		Function<Integer, Integer> g1 = x -> x * 2;//函数g给数字乘2

		//f&g 组合成一个函数h
		Function<Integer, Integer> h1 = f1.compose(g1);  //先给数字乘2，在给结果加1
		int result1 = h1.apply(1); //return 3
		System.out.println(result1);

		System.out.println(splitLine);
		Function<String, String> addHeader = Letter::addHeader;
		Function<String, String> transformationPipeline = addHeader.andThen(Letter::checkSpelling).andThen(Letter::addFooter);
		System.out.println(transformationPipeline.apply("--lambda-----From Raoul, Mario and Alan--labda----"));
	}
	
	
	/**
	 * Stream
	 */
	@Test
	public void test3() {
		inventory.stream()//获取流
				 .filter(d -> d.getWeight() > 100)//中间操作，过滤
				 .map(Apple::getColor)//中间操作，提取颜色
				 .skip(1L)//返回一个扔掉了前n个元素的流。
				 .limit(3)//中间操作，截断流
				 .distinct()//去重
				 .collect(toList())//将stream转换为List
				 .forEach(System.out::println);
	}
	
	
	/**
	 * map & flatMap
	 */
	@Test
	public void test4() {
		List<String> words = Arrays.asList("Hello","World");
		words.stream()
			 .map(word -> word.split(""))
			 .map(Arrays :: stream)//流集合
			 .distinct()
			 .collect(toList()).forEach(arrayStream -> arrayStream.forEach(System.out::println));
		
		
		System.out.println(splitLine);
		
		words.stream()
			 .map(word -> word.split(""))
			 .flatMap(Arrays :: stream)//flatmap方法让你把一个流中的每个值都换成另一个流，然后把所有的流连接起来成为一个流。
			 .distinct()
			 .collect(toList()).forEach(System.out::println);
	}
	
	
	/**
	 * 匹配
	 * anyMatch 流中是否有一个元素能匹配给定的谓词
	 * 
	 * allMatch	检查谓词是否匹配所有元素 
	 * 
	 * noneMatch 没有任何元素与给定的谓词匹配
	 */
	@Test
	public void test5() {
		if(menu.stream().anyMatch(Dish::isVegetarian)){
			System.out.println("The menu is (somewhat) vegetarian friendly!!");
		}

		
		if(menu.stream().allMatch(d -> d.getCalories() < 1000)){
			System.out.println("The menu is (all) Calories < 1000!!");
		}

		
		if(menu.stream().noneMatch(d -> d.getCalories() > 1000)){
			System.out.println("The menu is (noneMatch) Calories > 1000!!");
		}
	}
	
	
	/**
	 * 查找
	 * findFirst	查找第一个元素
	 * findAny		返回当前流中的任意元素
	 */
	@Test
	public void test6() {
		List<String> words = Arrays.asList("Hello","World");
		words.stream()
			 .map(word -> word.split(""))
			 .flatMap(Arrays :: stream)
			 .distinct()
			 .findAny()
			 .ifPresent(System.out::println);//H

		
		List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
		someNumbers.stream().map(x -> x * x)
							.filter(x -> x % 3 == 0)
							.findFirst()
							.ifPresent(System.out::println);// 9

	}
	
	/**
	 * 归约 reduce操作
	 * reduce接受两个参数：
	 * 		一个初始值，这里是0；
	 * 		一个BinaryOperator<T>来将两个元素结合起来产生一个新值，这里我们用的是 lambda (a, b) -> a + b。
	 * 		一个BinaryOperator<U> combiner
	 */
	@Test
	public void test7() {
		List<Integer> numbers = Arrays.asList(4, 5, 3, 9);
		Integer reduce = numbers.stream().reduce(0, (a, b) -> a + b);
		
		int sum = numbers.stream().reduce(0, Integer::sum);
		
		System.out.println(reduce);//21
		System.out.println(sum);//21
		
		numbers.stream().reduce((a, b) -> a + b).ifPresent(System.out::println);
		numbers.stream().reduce(Integer::sum).ifPresent(System.out::println);
		
		numbers.stream().reduce(Integer::max).ifPresent(System.out::println);

		numbers.stream().reduce(Integer::min).ifPresent(System.out::println);
		
		
		System.out.println(splitLine);
		
		List<Integer> accResult_ = numbers.parallelStream()//stream combiner 不会调用
		        .reduce(new ArrayList<Integer>(),
		                new BiFunction<List<Integer>, Integer, List<Integer>>() {
		                    @Override
		                    public List<Integer> apply(List<Integer> acc, Integer item) {
		                    	lock.lock();
		                        try {
									acc.add(item);
									System.out.println("item: " + item);
									System.out.println("acc+ : " + acc);
									System.out.println(String.format("BiFunction AND currentThreadName :%s", Thread.currentThread().getName()));
									return acc;
								} finally {
									lock.unlock();
								} 
		                    }
		                }, new BinaryOperator<List<Integer>>() {//Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result，combiner的作用在于合并每个线程的result得到最终结果。这也说明了了第三个函数参数的数据类型必须为返回数据类型了。
		                    @Override
		                    public List<Integer> apply(List<Integer> acc, List<Integer> item) {
		                    	lock.lock();
		                        try {
									System.out.println(String.format("BinaryOperator AND currentThreadName :%s", Thread.currentThread().getName()));
									acc.addAll(item);
									System.out.println("item: " + item);
									System.out.println("acc+ : " + acc);
									System.out.println("--------");
									return acc;
								} finally {
									lock.unlock();
								} 
		                    }
		                });
		System.out.println(String.format("accResult_ :%s", accResult_));


		long count = numbers.stream().count();
		System.out.println(count);//4

	}
	
	
	
	@Test
	public void test8() {
		Map<String, Apple> map = listToMap(inventory, "color");
		System.out.println(map);
		
		
		System.out.println(
				Arrays.asList("1","2","3").parallelStream().reduce(
				new StringBuilder(),
				(result, element)->
				result= result.append(element)
				,(u,t) -> u.append(t)).toString()
		);
	}
	
	
	/**
	 * 数值流：
	 *	 1.映射到数值流 ： 将流转换为特化版本的常用方法是mapToInt、 mapToDouble和mapToLong。(返回的是一个特化流，而不是Stream<T>) 
	 * 
	 * 	2.转换回对象流 boxed方法 有了数值流，你可能会想把它转换回非特化流.
	 * 
	 * 	3.默认值OptionalInt、 OptionalDouble和OptionalLong。
	 * 
	 * 数值范围 range和rangeClosed方法
	 * 
	 */
	@Test
	public void test9() {
		IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
		Stream<Integer> stream = intStream.boxed();
		OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
		
		IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
		
		//复合勾股定理的数组
		Stream<double[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100)
														.boxed()
														.flatMap(a ->
																	IntStream.rangeClosed(a, 100)
																			 .mapToObj(b -> new double[]{a, b, Math.sqrt(a*a + b*b)})
																			 .filter(t -> t[2] % 1 == 0)
																);
		pythagoreanTriples2.limit(5)
						   .forEach(t ->
						   	System.out.println(t[0] + ", " + t[1] + ", " + t[2])
						   	);


		System.out.println(String.format("intStream :%s", intStream));
		System.out.println(String.format("stream :%s", stream));
		System.out.println(String.format("maxCalories :%s", maxCalories.getAsInt()));
		System.out.println(String.format("evenNumbers :%s", evenNumbers.count()));

	}
	
	
	/**
	 * 构建流
	 * 		1.由值创建流 静态方法Stream.of
	 * 		2.由数组创建流 Arrays.stream
	 * 		3.由文件生成流
	 * 		4.由函数生成流：创建无限流 Stream.iterate和Stream.generate。
	 * @throws IOException 
	 */
	@Test
	public void test10() throws IOException {
		Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
		stream.map(String::toUpperCase).forEach(System.out::println);
		//可以使用empty得到一个空流，
		
		int[] numbers = {2, 3, 5, 7, 11, 13};
		int sum = Arrays.stream(numbers).sum();
		
		System.out.println(String.format("sum :%s", sum));
		long uniqueWords = 0;
		Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset());//流会自动关闭
//		lines.flatMap(line -> Arrays.stream(line.split(" "))).forEach(System.out::println);
		uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
		System.out.println(uniqueWords);
		
		
		Stream.iterate(0, n -> n + 2)//迭代
		  .limit(10)
		  .forEach(System.out::println);


		Stream.generate(Math::random)//生成
		  .limit(5)
		  .forEach(System.out::println);

	}
	
	
	/**
	 * 用流收集数据（Collectors类）
	 * 	1.归约和汇总
	 */
	@Test
	public void test11() {
		List<Dish> dishs = menu.stream().collect(toList());

		long howManyDishes = menu.stream().collect(counting());
		//简写
		long howManyDishes1 = menu.stream().count();
		System.out.println(String.format("howManyDishes :%s", howManyDishes));
		System.out.println(String.format("howManyDishes1 :%s", howManyDishes1));

		
		menu.stream().collect(maxBy(comparing(Dish::getCalories))).ifPresent(System.out::println);//最大值
		menu.stream().collect(minBy(comparing(Dish::getCalories))).ifPresent(System.out::println);//最小值
		int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));//求和
		
		double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));//平均数
		
		//joining在内部使用了StringBuilder来把生成的字符串逐个追加起来
		String shortMenu = menu.stream().map(Dish::getName).collect(joining());//连接字符串 joining
		String shortMenu1 = menu.stream().map(Dish::getName).collect(joining(" ,"));//连接字符串 joining

		
		int totalCalories1 = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));


		System.out.println(String.format("totalCalories :%s", totalCalories));
		System.out.println(String.format("avgCalories :%s", avgCalories));
		System.out.println(String.format("shortMenu :%s", shortMenu));
		System.out.println(String.format("shortMenu1 :%s", shortMenu1));
		
	}
	
	
	
	/**
	 * 用流收集数据（Collectors.groupingBy）
	 */
	@Test
	public void test12() {
		
		//把一个内层groupingBy传递给外层groupingBy，并定义一个为流中项目分类的二级标准
		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream().collect(
				groupingBy(Dish::getType,groupingBy(dish -> {
							if (dish.getCalories() <= 400) 
								return CaloricLevel.DIET;
							else if (dish.getCalories() <= 700) 
								return CaloricLevel.NORMAL;
							else 
								return CaloricLevel.FAT;
							}
						 )
					)
				);
		
		//按子组收集数据	传递给第一个groupingBy的第二个收集器可以是任何类型.
		Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
		
		Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(
																					groupingBy(Dish::getType,
																					maxBy(comparingInt(Dish::getCalories)))
																				);
		
		
		//普通的单参数groupingBy(f)（其中f是分类函数）实际上是groupingBy(f,toList())的简便写法.
		Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));


		Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream().collect(
																	groupingBy(Dish::getType, mapping(
																		dish -> {
																				 if (dish.getCalories() <= 400) 
																					 return CaloricLevel.DIET;
																				 else if (dish.getCalories() <= 700) 
																					 return CaloricLevel.NORMAL;
																				 else 
																					 return CaloricLevel.FAT;
																				  }, toSet() )
																			)
																	);

		
		/**
		输出
			{
				MEAT={DIET=[chicken], NORMAL=[beef], FAT=[pork]},
				FISH={DIET=[prawns], NORMAL=[salmon]},
				OTHER={DIET=[rice, seasonal fruit], NORMAL=[french fries, pizza]}
			}
		 */
		System.out.println(String.format("dishesByTypeCaloricLevel :%s", dishesByTypeCaloricLevel));
		

		/**
		 *return {MEAT=3, FISH=2, OTHER=4} 
		 */
		System.out.println(String.format("typesCount :%s", typesCount));
		
		
		/**
		 *return	Optional<Dish>		{FISH=Optional[salmon], OTHER=Optional[pizza], MEAT=Optional[pork]}
		 */
		System.out.println(String.format("mostCaloricByType :%s", mostCaloricByType));
		
		
		System.out.println(String.format("totalCaloriesByType :%s", totalCaloriesByType));
		System.out.println(String.format("caloricLevelsByType :%s", caloricLevelsByType));
		
		
	}
	
	
	
	
	/**
	 * 用流收集数据（Collectors.partitioningBy）
	 * 	分区
	 * 	区是分组的特殊情况：由一个谓词（返回一个布尔值的函数）作为分类函数，它称分区函数。
	 * 分区函数返回一个布尔值，意味着得到的分组Map的键类型是Boolean，于是它最多可以分为两组——true是一组， false是一组.
	 */
	@Test
	public void test13() {
		
		Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
		
		//也可以传递第二个收集器
		
		Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(
																			partitioningBy(
																				Dish::isVegetarian,groupingBy(Dish::getType))
																			);


		//return {false=[pork, beef, chicken, prawns, salmon],true=[french fries, rice, season fruit, pizza]}
		System.out.println(String.format("partitionedMenu :%s", partitionedMenu));
		
		/**
			return: 二级map：
			{false={FISH=[prawns, salmon], MEAT=[pork, beef, chicken]},
			true={OTHER=[french fries, rice, season fruit, pizza]}}
		*/
		System.out.println(String.format("vegetarianDishesByType :%s", vegetarianDishesByType));
		
	}
	
	
	
	public enum CaloricLevel { DIET, NORMAL, FAT }

	


	/**
		输出
			{
				MEAT={DIET=[chicken], NORMAL=[beef], FAT=[pork]},
				FISH={DIET=[prawns], NORMAL=[salmon]},
				OTHER={DIET=[rice, seasonal fruit], NORMAL=[french fries, pizza]}
			}
	*/

	
	
	 /**
     * list转为map的方法, list的包含的是实体对象, map的key为实体的主键
     * @param list
     * @param fieldName
     * @return
     */
    public <T>Map<String,T> listToMap(List<T> list, String fieldName) {
        Map<String, T> res = list.stream().collect(Collectors.toMap(
                e -> {
                    Class<?> c = e.getClass();
                    String key = "";
                    try {
                        Field id = c.getDeclaredField(fieldName);
                        id.setAccessible(true);
                        key = id.get(e) + "";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return key;
                },
                e -> e,
                (x, y) -> {
                    throw new AssertionError();
                },
                HashMap::new
        ));
        return res;
    }
	
	
	
	
	
	static class Letter {
		
		public static String addHeader(String text){
			return "From Raoul, Mario and Alan: " + text;
		}
		public static String addFooter(String text){
			return text + " Kind regards";
		}
		public static String checkSpelling(String text){
			return text.replaceAll("labda", "lambda       ");
		}
	}
	

}
