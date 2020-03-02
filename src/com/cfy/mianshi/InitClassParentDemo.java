package com.cfy.mianshi;
/**
*@author    created by ChenFangYa
*@date  2020年1月20日---下午2:31:46
*@action
*/
public class InitClassParentDemo {

	private int p1 = 1;
	private final int p2 = 2;
	private static int p3 = 3;
	
	{
		System.out.println(String.format("父类 ------构造代码块-------p1 = %s, p2 =  %s, p3 = %s", p1, p2, p3));
	}
	
	static {
		System.out.println(String.format("父类 ------静态代码块-------p3 = %s", p3));
	}

	public InitClassParentDemo() {
		System.out.println(String.format("父类 ------无参构造方法-------p1 = %s, p2 =  %s, p3 = %s", p1, p2, p3));
	}
	
	
	public int add() {
		return p2 + p3 - p1;
	}
	
	public static int reduce() {
		System.out.println(String.format("父类 -----  reduce方法------- p3 = %s", p3));
		return  p3 - 1;
	}
	
}
