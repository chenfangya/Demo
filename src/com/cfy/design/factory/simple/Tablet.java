package com.cfy.design.factory.simple;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---11:50:54
*@action
*/
public class Tablet extends Computer {

	@Override
	public void work() {
		System.out.println("Work by Tablet");
	}

	@Override
	public void chat() {
		System.out.println("chat by Tablet");
	}

	@Override
	public void watchMovie() {
		System.out.println("watchMovie by Tablet");
	}
}
