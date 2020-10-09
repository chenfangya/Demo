package com.cfy.design.prototype;

import java.util.Scanner;

/**
 * @author created by ChenFangYa
 * @date 2020-9-21---11:33:39
 * @action
 */
public class Circle implements Shape {
	
	public Object clone() {
		Circle w = null;
		try {
			w = (Circle) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("拷贝圆失败!");
		}
		return w;
	}

	public void countArea() {
		System.out.print("这是一个圆，请输入圆的半径：");
		int r = new Scanner(System.in).nextInt();
		System.out.println("该圆的面积=" + 3.1415 * r * r + "\n");
	}
}
