package com.cfy.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import org.junit.Test;

import com.cfy.entity.ExchangeService;
import com.cfy.entity.Quote;
import com.cfy.entity.Shop;
import com.cfy.entity.ShopAsync;

/**
*@author    created by ChenFangYa
*@date  2020年1月16日---下午4:47:21
*@action
*/
public class Discount {

	@Test
	public void test1() {
		long start = System.nanoTime();
		System.out.println(findPricesByDiscount("LetsSaveBig"));
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");//Price returned after 10004 msecs
	}
	
	@Test
	public void test2() throws Exception {
		long start = System.nanoTime();
		System.out.println(findPricesByDiscountAndCompletableFuture("LetsSaveBig"));
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");//Price returned after 2005 msecs
	}
	
	@Test
	public void test3() throws Exception {
		long start = System.nanoTime();
		System.out.println(findPrices7("LetsSaveBig"));
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");//Price returned after 1003 msecs
	}
	
	
	public List<String> findPrices7(String product) { 
		List<ShopAsync> shops = Arrays.asList(new ShopAsync("BestPrice"), new ShopAsync("LetsSaveBig"), new ShopAsync("MyFavoriteShop"),
				new ShopAsync("BuyItAll"), new ShopAsync("ShopEasy"));
		
		List<CompletableFuture<Double>> priceFutures = new ArrayList<>();
        for (ShopAsync shop : shops) {
            CompletableFuture<Double> futurePriceInUSD = 
                CompletableFuture.supplyAsync(() -> shop.getPriceAsync(product))//创建第一个任务查询商店取得商品的价格
                .thenCombine(
                    CompletableFuture.supplyAsync(
                        () ->  ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),//创建第二个独立任务，查询美元和欧元之间的转换汇率
                    (price, rate) -> {
						try {
							return (price.get() * rate);
						} catch (Exception e) {
							throw new  RuntimeException(e);
						}
					}//通过乘法整合得到的商品价格和汇率
                );
            priceFutures.add(futurePriceInUSD);
        }
        List<String> prices = priceFutures
                .stream()
                .map(CompletableFuture::join)
                .map(price -> /*shop.getName() +*/ " price is " + price)
                .collect(Collectors.toList());
        return prices;
	}

	
	/**
	 * 构造同步和异步操作 在需要的地方把它们变成了异步操作。
	 * 
	 * 获取价格
	 * 这三个操作中的第一个只需要将Lambda表达式作为参数传递给supplyAsync工厂方法就可以以异步方式对shop进行查询。
	 * 第一个转换的结果是一个Stream<CompletableFuture>，一旦运行结束，每个CompletableFuture对象中都会包含对应shop返回的字符串。
	 * 
	 * 解析报价
	 * 现在需要进行第二次转换将字符串转变为订单。由于一般情况下解析操作不涉及任何远程服务，也不会进行任何I/O操作，它几乎可以在第一时间进行，所以能够采用同步操作，不会带来太多的延迟。
	 * 由于这个原因，你可以对第一步中生成的CompletableFuture对象调用它的thenApply，将一个由字符串转换Quote的方法作为参数传递给它（直到调用的CompletableFuture执行结束，使用的thenApply方法都不会阻塞你代码的执行）
 	 *	为计算折扣价格构造Future
	 * 第三个map操作涉及联系远程的Discount服务，为从商店中得到的原始价格申请折扣率。
	 * 这一转换与前一个转换又不大一样，因为这一转换需要远程执行（或者，就这个例子而言，它需要模拟远程调用带来的延迟），
	 * 出于这一原因，希望它能够异步执行。
	 * 为了实现这一目标，我们像第一个调用传递getPrice给supplyAsync那样，
	 * 将这一操作以Lambda表达式的方式传递给了supplyAsync工厂方法，该方法最终会返回另一个CompletableFuture对象。
	 * 到目前为止，你已经进行了两次异步操作，用了两个不同的CompletableFutures对象进行建模，你希望能把它们以级联的方式串接起来进行工作
	 * 
	 * 从shop对象中获取价格，接着把价格转换为Quote。  
	 * 拿到返回的Quote对象，将其作为参数传递给Discount服务，取得最终的折扣价格。
	 * 
	 * Java 8的 CompletableFuture API提供了名为thenCompose的方法，它就是专门为这一目的而设计的，
	 * thenCompose方法允许你对两个异步操作进行流水线，第一个操作完成时，将其结果作为参数传递给第二个操作。
	 * 就是你可以创建两个CompletableFutures对象，对第 一 个 CompletableFuture 对 象 调 用 thenCompose ，
	 * 并 向其 传 递一 个函 数 。当 第一
	 * 个CompletableFuture执行完毕后，它的结果将作为该函数的参数，
	 * 这个函数的返回值是以第一个CompletableFuture的返回做输入计算出的第二个CompletableFuture对象。
	 * 使用这种方式，即使Future在向不同的商店收集报价，主线程还是能继续执行其他重要的操作，比如响应UI事件。
	 * 
	 * 将这三次map操作的返回的Stream元素收集到一个列表，你就得到了一个List<CompletableFuture>，等这些CompletableFuture对象最终执行完毕，就可以利用join取得它们的返回值。
	 * 
	 * @param product
	 * @return
	 */
	public List<String> findPricesByDiscountAndCompletableFuture(String product) {
		List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"), new Shop("MyFavoriteShop"),
				new Shop("BuyItAll"), new Shop("ShopEasy"));
		
		final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), // 创建一个线程池，线程池中线程的数 目为
				// 100和商店数目二者中较小的一个值
								new ThreadFactory() {
									public Thread newThread(Runnable r) {
										Thread t = new Thread(r);
										t.setDaemon(true);// 使用守护线程——这种方式不会阻止程序的关停
										return t;
									}
								});
		
		 List<CompletableFuture<String>> priceFutures = shops.stream()
				 				.map(shop -> CompletableFuture.supplyAsync(//以异步方式取得每个shop中指定产品的原始价格
				 							() -> shop.getPrice(product), executor))
				 				.map(future -> future.thenApply(Quote::parse))//Quote对象存在时，对其返回的值进行转换
				 				.map(future -> future.thenCompose(//使用另一个异步任务构造期望的Future，申请折扣
				 						quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
				 				.collect(Collectors.toList());
		 
		 return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());//等待流中的所有Future执行 完毕，并提取各自的返回值
	}

	
	
	/**
	 * 使用 Discount 服务 
	 * 第一个操作将每个shop对象转换成了一个字符串，该字符串包含了该 shop中指定商品的价格和折扣代码。
	 * 第二个操作对这些字符串进行了解析，在Quote对象中对它们进行转换。
	 * 最终，第三个map会操作联系远程的Discount服务，计算出最终的折扣价格，并返回该价格及提供该价格商品的shop。
	 * 这次执行耗时10秒，因为顺序查询5个商店耗时大约5秒， 现在又加上了Discount服务为5个商店返回的价格申请折扣所消耗的5秒钟。
	 * 
	 * @param product
	 * @return
	 */
	public List<String> findPricesByDiscount(String product) {
		List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"), new Shop("MyFavoriteShop"),
				new Shop("BuyItAll"), new Shop("ShopEasy"));
		return shops.stream()
		.map(shop -> shop.getPrice(product))//取得每个shop对象中商品的原始价格
		.map(Quote::parse)//在 Quote 对 象 中对shop返回的字符串进行转换
		.map(Discount::applyDiscount)//联系Discount服务，为每个Quote申请折扣
		.collect(Collectors.toList());
	}

	
	public enum Code {
		NONE(0), SILVER(0), GOLD(10), PLATINUM(15), DIAMOND(20);
		private final int percentage;

		Code(int percentage) {
			this.percentage = percentage;
		}
	}
	
	
	public static String applyDiscount(Quote quote) {
		return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
	}

	private static double apply(double price, Code code) {
		delay();
		return price * (100 - code.percentage) / 100;
	}
	

	
	public double getPrice(String product) {
		return calculatePrice(product);
	}
	
	private double calculatePrice(String product) {
		delay();
		Random random = new Random();
		return random.nextDouble() * product.charAt(0) + product.charAt(1);
	}
	
	/**
	模拟1秒钟延迟的方法
	*/
	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	
	public void doSomethingElse() {
		System.out.println("doSomethingElse");
		try {
			Thread.sleep(2000);
			System.out.println("doSomethingElse------------END");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
