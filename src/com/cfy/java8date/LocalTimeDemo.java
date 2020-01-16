package com.cfy.java8date;

import java.time.LocalTime;

import org.junit.Test;

/**
 * @author created by ChenFangYa
 * @date 2020年1月17日---上午9:37:49
 * @action
 */
public class LocalTimeDemo {

	@Test
	public void test() {
//		LocalTime time = LocalTime.of(13, 45, 20);//13:45:20
		LocalTime time = LocalTime.now();// h:m:s:ms
		int hour = time.getHour();
		int minute = time.getMinute();
		int second = time.getSecond();

		LocalTime timeParse = LocalTime.parse("13:45:20");// 13:45:20

		System.out.println(time);
		System.out.println(hour);
		System.out.println(minute);
		System.out.println(second);
		System.out.println(timeParse);
	}

}
