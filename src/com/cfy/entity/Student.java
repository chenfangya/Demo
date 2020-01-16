package com.cfy.entity;
/**
*@author    created by ChenFangYa
*@date  2019年12月24日---上午10:56:37
*@action
*/
public class Student {

	private int age;
	private String name;
	
	public Student() {
		super();
		System.out.println("线程   " + Thread.currentThread().getName() + " 初始化" + this.getClass().getName());
	}
	public int getAge() {
		return age;
	}
	public String getName() {
		return name;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public final Object obj = new Object() {
		@Override
		protected void finalize() throws Throwable {
			System.out.println("finalize");
			super.finalize();
		}
	};
}
