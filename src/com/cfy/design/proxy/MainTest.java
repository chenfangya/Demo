package com.cfy.design.proxy;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

/**
 * @author created by ChenFangYa
 * @date 2020-9-21---14:43:20
 * @action
 */
public class MainTest {

	@Test
	public void testStaticProxy() {
		BuyHouse buyHouse = new BuyHouseImpl();
		buyHouse.buyHosue();
		BuyHouseStaticProxy buyHouseProxy = new BuyHouseStaticProxy(buyHouse);
		buyHouseProxy.buyHosue();
	}

	@Test
	public void testDynamicProxy() {
		BuyHouse buyHouse = new BuyHouseImpl();
		BuyHouse proxyBuyHouse = (BuyHouse) Proxy.newProxyInstance(BuyHouse.class.getClassLoader(),
				new Class[] { BuyHouse.class }, new DynamicProxyHandler(buyHouse));
		proxyBuyHouse.buyHosue();
	}
}
