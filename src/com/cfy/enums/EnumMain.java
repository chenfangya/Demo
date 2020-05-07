package com.cfy.enums;

import java.util.EnumSet;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月19日---下午3:32:36
*@action
*/
public class EnumMain {

	@Test
	public void test() {
		double pay = PayrollDay.FRIDAY.pay(12, 3);
		
		PayrollDay.FRIDAY.testSet(EnumSet.of(PayrollDay.FRIDAY, PayrollDay.MONDAY));
		
		System.out.println(pay);
	}
}
