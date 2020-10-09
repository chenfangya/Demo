package com.cfy.design.factory.simple;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---11:49:01
*@action
*/
public class Laptop extends Computer {

	@Override
	public void work() {
		System.out.println("Work by Laptop");
	}

	@Override
	public void chat() {
		System.out.println("chat by Laptop");
	}

	@Override
	public void watchMovie() {
		System.out.println("watchMovie by Laptop");
	}
}
