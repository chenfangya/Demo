package com.cfy.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
*@author    created by ChenFangYa
*@date  2020年1月19日---下午3:19:50
*@action
*/
public enum PayrollDay {

	MONDAY(PayType.WEEKDAY),
	TUESDAY(PayType.WEEKDAY),
	WEDNESDAY(PayType.WEEKDAY),
	THURSDAY(PayType.WEEKDAY),
	FRIDAY(PayType.WEEKDAY),
	SATURDAY(PayType.WEEKEND),
	SUNDAY(PayType.WEEKEND),;
	
	private final PayType payType;
	
	private static final Map<PayType, List<PayrollDay>> m = new EnumMap<>(PayType.class);
	
	private PayrollDay(PayType payType) {
		this.payType = payType;
	}
	
	static {
		Arrays.stream(PayType.values()).forEach(v -> m.put(v, new ArrayList<PayrollDay>()));
		Arrays.stream(PayrollDay.values()).forEach(v -> m.get(v.payType).add(v));
		System.out.println(m);
	}
	
	double pay(double hoursWorked, double payRate) {
		return payType.pay(hoursWorked, payRate);
	}
	
	void testSet(Set<PayrollDay> set) {
		System.out.println(set);
	}
	
	private enum PayType {
		WEEKDAY {
			double overtimePay(double hours, double payRate) {
				return hours <= HOURS_PER_SHIFT ? 0:
					(hours - HOURS_PER_SHIFT) * payRate /2;
			}
		}, 
		WEEKEND {
			double overtimePay(double hours, double payRate) {
				return hours * payRate / 2;
			}
		},;
		
		private static final int HOURS_PER_SHIFT = 8;
		
		abstract double overtimePay(double hours, double payRate);
		
		double pay(double hoursWorked, double payRate) {
			double basePay = hoursWorked * payRate;
			return basePay + overtimePay(hoursWorked, payRate);
		}
	}
}
