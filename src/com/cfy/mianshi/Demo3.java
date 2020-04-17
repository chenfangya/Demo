package com.cfy.mianshi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * @author created by ChenFangYa
 * @date 2019年12月27日---下午4:31:39
 * @action
 */
public class Demo3 {
	
	@Test
	public void test11() {
		String s = null + "";
		System.out.println(s.equals("null"));
		System.out.println(s == null);
	}

	@Test
	public void test1() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");

		Set<String> keySet = map.keySet();
		System.out.println("keySet is : " + keySet.toString());
		System.out.println("map keySet clear before " + map.toString());
		keySet.clear();
		System.out.println("map keySet clear after " + map.toString());
	}

	@Test
	public void test2() {
		long start = System.currentTimeMillis();
		long sum = 0L;

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println("用时：" + (System.currentTimeMillis() - start) + ", 值 ： " + sum);
	}

	@Test
	public void test3() {
		System.out.println(dayOfTheWeek(15, 1, 2020));
	}

	@Test
	public void test4() {
		System.out.println(Q_rsqrt(9));
	}
	
	@Test
	public void test5() {
		HanoiTowers1(3,'A','B','C');
	}
	
	@Test
	public void test6() {
		int j = 0;
	    for (int i = 0; i < 10; i++) {
	        j = (j++);
	    }
	    System.out.println(j);
	}

	/**
	 * 计算某个日期是星期几：
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return 0 ~ 6 分别代表 周日到 周六
	 */
	private int dayOfTheWeek(int day, int month, int year) {
		int m = month;
		int y = year;
		if (month <= 2) {
			m += 12;
			y -= 1;
		}

		int c = y / 100;
		y = y % 100;
		int w = (y + y / 4 + c / 4 - 2 * c + (26 * (m + 1) / 10) + day - 1) % 7;
		return (w + 7) % 7;
	}

	/**
	 * 平方根倒数速算法
	 * 
	 * @param number
	 * @return
	 */
	private float Q_rsqrt(float number) {
		int i;
		float x2;
		float y;
		float threehalfs = 1.5F;
		x2 = number * 0.5F;
		y = number;
		i = Float.floatToRawIntBits(y); // evil floating point bit level hacking
		i = 0x5f3759df - (i >> 1); // what the fuck?
		y = Float.intBitsToFloat(i);
		y = y * (threehalfs - (x2 * y * y)); // 1st iteration
		y = y * (threehalfs - (x2 * y * y)); // 2nd iteration, this can be removed
		return y;
	}
	
	/**
     * 将A汉诺塔上的n个盘子通过C移动到B的递归方法
     * @param n   //汉诺塔上盘子的个数
     * @param A   //开始时有盘子的汉诺塔
     * @param B   //要将盘子移动到上面的目标汉诺塔
     * @param C   //中介汉诺塔
     * @throws IllegalArgumentException when n <= 0
     */
    public void HanoiTowers1(int n,char A,char B,char C){
        if(n <= 0){
            throw new IllegalArgumentException("n must be >=1");
        }
        if(n == 1){
            System.out.println(A + "->" + B);
        }
        else{
            HanoiTowers1(n - 1, A, C, B);     // 将除去最大的盘子的n个盘子从A通过B移动到C
            System.out.println(A + "->" + B);//将最大的盘子从A移动到B
            HanoiTowers1(n - 1, C, B, A);     //将除去最大的盘子的n-1个盘子从C通过A移动到B
        }
    }
}
