package com.cfy.design.adapter;

import java.util.Map; //旧系统接口

public interface IUserInfo {
	
	String getNumber();

	String getId();

	String getHomeAddress();

	Map<String, String> getTeles();

	String getName();

}