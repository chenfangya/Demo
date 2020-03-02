package com.cfy.mianshi;
/**
*@author    created by ChenFangYa
*@date  2020年1月20日---下午2:28:54
*@action
*/
public class InitClassDemo extends InitClassParentDemo{

	private int i1 = 1;
	private final int i2 = 2;
	private static int i3 = 3;
	
	{
		System.out.println(String.format("构造代码块-------i1 = %s, i2 =  %s, i3 = %s", i1, i2, i3));
	}
	
	static {
		System.out.println(String.format("静态代码块-------i3 = %s", i3));
	}

	public InitClassDemo() {
		super();
		System.out.println(String.format("构造方法-------i1 = %s, i2 =  %s, i3 = %s", i1, i2, i3));
	}
	
	public int add() {
		System.out.println("add 方法");
		return i2 + i3 - i1 + 1;
	}
	
	public static int reduce() {
		System.out.println(String.format("reduce  方法-------i3 = %s", i3));
		return  i3 - 1;
	}
	
}
