package com.cfy.design.proxy;

public class BuyHouseStaticProxy implements BuyHouse {

	private BuyHouse buyHouse;

	public BuyHouseStaticProxy(final BuyHouse buyHouse) {
		this.buyHouse = buyHouse;
	}

	@Override
	public void buyHosue() {
		System.out.println("买房前准备");
		buyHouse.buyHosue();
		System.out.println("买房后装修");

	}
}