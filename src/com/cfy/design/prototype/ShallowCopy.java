package com.cfy.design.prototype;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---10:29:22
*@action
*	1.当类的成员变量是基本数据类型时，浅拷贝会复制该属性的值赋值给新对象。
*	2、当成员变量是引用数据类型时，浅拷贝复制的是引用数据类型的地址值。
*	这种情况下，当拷贝出的某一个类修改了引用数据类型的成员变量后，会导致所有拷贝出的类都发生改变。
*/

public class ShallowCopy implements Cloneable {

	private String name;
	private int age;

	public ShallowCopy(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "ShallowCopy [name=" + name + ", age=" + age + "]";
	}
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		ShallowCopy shallowCopy = null;
		try {
			shallowCopy = (ShallowCopy) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return shallowCopy;
	}
}
