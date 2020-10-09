package com.cfy.design.singleton;

/**
 * @author created by ChenFangYa
 * @date 2020年5月8日---下午3:12:10
 * @action
 */
public enum EnumSingleton {
	
	INSTANCE,
	;
	
	private Student student = new Student();

	public Student getStudent() {
		return student;
	}
}