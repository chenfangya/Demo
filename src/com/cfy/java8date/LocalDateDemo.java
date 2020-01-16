package com.cfy.java8date;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

/**
*@author    created by ChenFangYa
*@date  2020年1月17日---上午9:37:49
*@action
*/
public class LocalDateDemo {

	@Test
	public void test() {
		LocalDate date = LocalDate.now();
		int year = date.getYear();
		Month month = date.getMonth();
		int day = date.getDayOfMonth();
		DayOfWeek dow = date.getDayOfWeek();//WEDNESDAY
		int len = date.lengthOfMonth();//当月天数
		boolean leap = date.isLeapYear();//是否闰年
		
		LocalDate dateParse = LocalDate.parse("2018-10-31");//2018-10-31

		System.out.println(date);
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);
		System.out.println(dow);
		System.out.println(len);
		System.out.println(leap);
		System.out.println(dateParse);
	}
	
	/**
	 * 使用TemporalField读取LocalDate的值
	 */
	@Test
	public void test1() {
		LocalDate date = LocalDate.now();
		int year = date.get(ChronoField.YEAR);
		int month = date.get(ChronoField.MONTH_OF_YEAR);
		int day = date.get(ChronoField.DAY_OF_MONTH);
		
		System.out.println(date);
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);

	}
	
	
	
	@Test
	public void test2() {
		LocalDate date = LocalDate.parse("2020-01-17");
		LocalDate date1 = date.withYear(2011);//2011-01-17
		LocalDate date2 = date1.withDayOfMonth(25);//2011-01-25
		LocalDate date3 = date2.with(ChronoField.MONTH_OF_YEAR, 9);//2011-09-25
		
		System.out.println(date);
		System.out.println(date1);
		System.out.println(date2);
		System.out.println(date3);
		
	}
	
	
	@Test
	public void test3() {
		LocalDate date1 = LocalDate.of(2018, 10, 31);//2018-10-31
		LocalDate date2 = date1.plusWeeks(1);//2018-11-07
		LocalDate date3 = date2.minusYears(3);//2015-11-07
		LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS);//2016-05-07
		
		System.out.println(date1);
		System.out.println(date2);
		System.out.println(date3);
		System.out.println(date4);
	}
	

	@Test
	public void test4() {
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));//这个周日
		LocalDate date3 = date2.with(TemporalAdjusters.lastDayOfMonth());//当月最后一天
		
		System.out.println(date1);
		System.out.println(date2);
		System.out.println(date3);
	}

	
	
	
	@Test
	public void test5() {
		LocalDate date = LocalDate.now();
		date = date.with(temporal -> {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			System.out.println("dow: " + dow);
			int dayToAdd = 1;
			if (dow == DayOfWeek.FRIDAY)
				dayToAdd = 3;
			else if (dow == DayOfWeek.SATURDAY)
				dayToAdd = 2;
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);//加	dayToAdd 天
		});
		System.out.println(date);
	}

	
	
	@Test
	public void test6() {
		LocalDate date = LocalDate.of(2018, 10, 31);
		String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
		String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
		System.out.println(s1);//20181031
		System.out.println(s2);//2018-10-31

		LocalDate date1 = LocalDate.parse("20181031", DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate date2 = LocalDate.parse("2018-10-31", DateTimeFormatter.ISO_LOCAL_DATE);
		System.out.println(date1 );//2018-10-31
		System.out.println(date2 );//2018-10-31
	}

	
	@Test
	public void test7() {
		LocalDate date = LocalDate.of(2018, 10, 31);//2018-10-31
		//Value(DayOfMonth)'.'' 'Text(MonthOfYear)' 'Value(YearOfEra,4,19,EXCEEDS_PAD)
		DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.CHINA);//yyyy-MM-dd
		LocalDate date1 = LocalDate.of(2018, 10, 31);//2018-10-31
		String formattedDate = date.format(italianFormatter); // 31 - 十月 - 2018
		LocalDate date2 = LocalDate.parse(formattedDate, italianFormatter);//2018-10-31
		
		System.out.println(date);
		System.out.println(italianFormatter);
		System.out.println(date1);
		System.out.println(formattedDate);
		System.out.println(date2);
	}


	

	@Test
	public void test8() {
		LocalDate date = LocalDate.of(2018, 10, 31);//2018-10-31
		
		DateTimeFormatter italianFormatter = new DateTimeFormatterBuilder()
				.appendText(ChronoField.DAY_OF_MONTH)
				.appendLiteral("-")
				.appendText(ChronoField.MONTH_OF_YEAR)
				.appendLiteral("-")
				.appendText(ChronoField.YEAR)
				.parseCaseInsensitive()
				.toFormatter(Locale.CHINA);
		String format = date.format(italianFormatter);//31-十月-2018
		LocalDate parse = LocalDate.parse(format, italianFormatter);//2018-10-31
		
		System.out.println(date);
		System.out.println(format);
		System.out.println(parse);
	}

	
	
	
	/**
	 * @return
	 * Europe/Rome 
	 * Asia/Shanghai 
	 * 2018-10-31 
	 * 2018-10-31T00:00+01:00[Europe/Rome]
	 * 2018-10-31T13:45 
	 * 2018-10-31T13:45+01:00[Europe/Rome] 
	 * 2018-11-05T09:16:33.478Z
	 * 2018-11-05T10:16:33.478+01:00[Europe/Rome]
	 * 
	 */
	@Test
	public void test9() {
		ZoneId romeZone = ZoneId.of("Europe/Rome");
		
		ZoneId zoneId = TimeZone.getDefault().toZoneId();
		
		
		LocalDate date = LocalDate.of(2018, Month.OCTOBER, 31);
		ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
		LocalDateTime dateTime = LocalDateTime.of(2018, Month.OCTOBER, 31, 13, 45);
		ZonedDateTime zdt2 = dateTime.atZone(romeZone);
		Instant instant = Instant.now();
		ZonedDateTime zdt3 = instant.atZone(romeZone);
		
		System.out.println(romeZone);
		System.out.println(zoneId);
		System.out.println(date);
		System.out.println(zdt1);
		System.out.println(dateTime);
		System.out.println(zdt2);
		System.out.println(instant);
		System.out.println(zdt3);
	}

	
	
	
}
