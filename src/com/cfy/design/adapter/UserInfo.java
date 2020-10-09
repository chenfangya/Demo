package com.cfy.design.adapter;

import java.util.Map;

public class UserInfo implements IUserInfo {
	private String number;
	private String id;
	private String homeAddress;
	private Map<String, String> teles;
	private String name;

	public UserInfo() {

	}

	public UserInfo(String number, String id, String homeAddress, Map<String, String> teles, String name) {
		this.number = number;
		this.id = id;
		this.homeAddress = homeAddress;
		this.teles = teles;
		this.name = name;
	}

	@Override
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Override
	public Map<String, String> getTeles() {
		return teles;
	}

	public void setTeles(Map<String, String> teles) {
		this.teles = teles;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}