package com.cfy.design.adapter;

public interface IFacultyMember { // 新系统的接口
	
	String getIdentification();

	String getName();

	String getContactTelephone();

	String toString();

	boolean equals(Object o);
}