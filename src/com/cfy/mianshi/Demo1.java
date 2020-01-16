package com.cfy.mianshi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by ChenFangYa
 * @date 2019年11月15日---下午3:10:13
 * @action
 */
public class Demo1 {
	
	public static void main(String[] args) {
		System.out.println(isHappy(19));
	}

	/**
	 * 一个“快乐数”定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，
	 * 然后重复这个过程直到这个数变为 1，也可能是无限循环但始终变不到
	 * 1。如果可以变为 1，那么这个数就是快乐数。
	 * 
	 * @param n
	 * @return
	 */
	public static boolean isHappy(int n) {
		boolean flag = true;
		// 用来添加每次计算后的结果
		List<Integer> list = new ArrayList<Integer>();
		while (n != 1) {
			// 计算数字长度
			String s = "" + n;
			int len = s.length();
			int sum = 0;
			// 对该数的每一位平方后相加
			for (int i = len; i > 0; i--) {
				int temp = n % (int) Math.pow(10, i) / (int) Math.pow(10, i - 1);
				sum += (int) Math.pow(n % (int) Math.pow(10, i) / (int) Math.pow(10, i - 1), 2);
			}
			// 如果该集合已经包含sum，那么就是死循环，直接返回false
			if (list.contains(sum)) {
				return !flag;
			}
			// 不包含，将sum加入集合中
			list.add(sum);
			// 将sum的值赋给n
			n = sum;
		}
		return flag;
	}
}
