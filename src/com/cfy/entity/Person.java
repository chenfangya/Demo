package com.cfy.entity;
/**
*@author    created by ChenFangYa
*@date  2019年12月30日---上午9:49:37
*@action
*/
public class Person {

	private String lastName;
	private String firstName;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Person() {
		super();
		System.out.println("线程   " + Thread.currentThread().getName() + " 初始化" + this.getClass().getName());
	}
}
