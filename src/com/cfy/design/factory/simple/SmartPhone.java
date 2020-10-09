package com.cfy.design.factory.simple;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---11:50:20
*@action
*/
public class SmartPhone extends Computer {

	@Override
	public void work() {
		System.out.println("Work by SmartPhone");
	}

	@Override
	public void chat() {
		System.out.println("chat by SmartPhone");
	}

	@Override
	public void watchMovie() {
		System.out.println("watchMovie by SmartPhone");
	}
}
