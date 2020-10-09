package com.cfy.design.singleton;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---10:10:28
*@action
*/

public class Student {
	private int age;
		private String name;
		public int getAge() {
			return age;
		}
		public Student() {
			super();
			System.out.println("线程   " + Thread.currentThread().getName() + " 初始化" + this.getClass().getName());
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
}
