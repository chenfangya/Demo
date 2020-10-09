package com.cfy.design.prototype;

import java.util.Scanner;

/**
 * @author created by ChenFangYa
 * @date 2020-9-21---11:34:51
 * @action
 */
public class Square implements Shape {

	public Object clone() {
		Square b = null;
		try {
			b = (Square) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("拷贝正方形失败!");
		}
		return b;
	}

	public void countArea() {
		System.out.print("这是一个正方形，请输入它的边长：");
		int a = new Scanner(System.in).nextInt();
		System.out.println("该正方形的面积=" + a * a + "\n");
	}
}
