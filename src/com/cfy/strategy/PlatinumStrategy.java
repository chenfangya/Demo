package com.cfy.strategy;

public class PlatinumStrategy implements Strategy {

	@Override
	public double compute(long money) {

		System.out.println("白金会员 优惠150元");
		return money - 150;
	}

	// type 返回
	@Override
	public int getType() {
		return UserType.PLATINUM_VIP.getCode();
	}
}