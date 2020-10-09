package com.cfy.design.factory.method;
/**
*@author    created by ChenFangYa
*@date  2020-9-21---13:57:52
*@action
*/
public class ConcreateFactoryB extends AbstractFactory {

	@Override
	public IProduct createProduct() {
		return new ProductB();
	}

}
