package com.cfy.entity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ShopAsync {

	private final String name;
	private final Random random;
	private static final DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

	public ShopAsync(String name) {
		this.name = name;
		random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
	}

	public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
                    double price = calculatePrice(product);
                    futurePrice.complete(price);
        }).start();
        return futurePrice;
    }

	public double calculatePrice(String product) {
		delay();
		return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
	}

	public String getName() {
		return name;
	}

	public static void delay() {
		int delay = 1000;
		// int delay = 500 + RANDOM.nextInt(2000);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static double format(double number) {
		synchronized (formatter) {
			return new Double(formatter.format(number));
		}
	}
}
