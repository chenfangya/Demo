package com.cfy.java8date;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月17日---上午10:22:29
*@action	java.time.Instant类对时间建模的方式，以Unix元年时间（传统的设定为UTC时区1970年1月1日午夜时分）开始所经历的秒数进行计算.
*/
public class InstantDemo {

	@Test
	public void test() {
		Instant i1 = Instant.ofEpochSecond(3);
		Instant i2 = Instant.ofEpochSecond(3, 0);
		Instant i3 = Instant.ofEpochSecond(2, 1_000_000_000);//2 秒 之 后 再 加 上100万纳秒（ 1秒）
		Instant i4 = Instant.ofEpochSecond(4, -1_000_000_000);//4秒之前的100万纳秒（ 1秒）
		
		
		System.out.println(i1);//1970-01-01T00:00:03Z
		System.out.println(i2);//1970-01-01T00:00:03Z
		System.out.println(i3);//1970-01-01T00:00:03Z
		System.out.println(i4);//1970-01-01T00:00:03Z

	}
	
	
	@Test
	public void test1() {
		Period tenDays = Period.between(LocalDate.of(2018, 10, 21),
				LocalDate.of(2018, 10, 31));
		System.out.println(tenDays.getDays());//10
	}
	
	
	@Test
	public void test2() {
		Duration threeMinutes = Duration.ofMinutes(3);
		Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);
		Period tenDays = Period.ofDays(10);
		Period threeWeeks = Period.ofWeeks(3);
		Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
		
		System.out.println(threeMinutes);//PT3M
		System.out.println(threeMinutes2);//PT3M
		System.out.println(tenDays);//P10D
		System.out.println(threeWeeks);//P21D
		System.out.println(twoYearsSixMonthsOneDay);//P2Y6M1D
	}

}
