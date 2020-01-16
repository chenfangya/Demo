package com.cfy.strategy;

public class GoldStrategy implements Strategy {

	@Override
	public double compute(long money) {

		System.out.println("黄金会员 优惠100元");
		return money - 100;
	}

	// type 返回
	@Override
	public int getType() {
		return UserType.GOLD_VIP.getCode();
	}
}