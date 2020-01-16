package com.cfy.java8date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import org.junit.Test;

/**
 * @author created by ChenFangYa
 * @date 2020年1月17日---上午9:37:49
 * @action
 */
public class LocalDateTimeDemo {

	@Test
	public void test() {
		LocalDate date = LocalDate.of(2018, 10, 31);
		LocalTime time = LocalTime.of(13, 45, 20);
		// 2018-10-31T13:45:20
		LocalDateTime dt1 = LocalDateTime.of(2018, Month.OCTOBER, 31, 13, 45, 20);
		LocalDateTime dt2 = LocalDateTime.of(date, time);
		LocalDateTime dt3 = date.atTime(13, 45, 20);
		LocalDateTime dt4 = date.atTime(time);
		LocalDateTime dt5 = time.atDate(date);
		
		System.out.println(dt1);
		System.out.println(dt2);
		System.out.println(dt3);
		System.out.println(dt4);
		System.out.println(dt5);
	}

	@Test
	public void test1() {
		
		LocalDateTime now = LocalDateTime.now();
		
		LocalDate date = now.toLocalDate();
		LocalTime time = now.toLocalTime();

		System.out.println(now);//2020-01-17T10:21:03.380
		System.out.println(date);//2020-01-17
		System.out.println(time);//10:21:03.380
	}
	

}
