package com.cfy.entity;

public enum InstanceDemo {
	INSTANCE,

	INSTANCE2,;
	private Student student = new Student();
	private Person person = new Person();

	/*
	 * private InstanceDemo() { student = new Student(); }
	 */
	
	public Student getStudent() {
		return student;
	}
//	
	public Person getPerson() {
		return person;
	}
}