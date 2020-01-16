package com.cfy.strategy;

/**
 * @author created by ChenFangYa
 * @date 2020年1月16日---上午10:10:43
 * @action
 */
public class Demo {

	public static void main(String[] args) {
		
		System.out.println(getResult(1000, UserType.GOLD_VIP.getCode()));
	}

	private static double getResult(long money, int code) {

		if (money < 1000) {
			return money;
		}
		Strategy strategy = StrategyFactory.getInstance().get(code);

		if (strategy == null) {
			throw new IllegalArgumentException("please input right type");
		}

		return strategy.compute(money);
	}
}
